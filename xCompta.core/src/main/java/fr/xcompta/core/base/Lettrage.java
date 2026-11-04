package fr.xcompta.core.base;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.lettrage.LettrageCompteDifferentException;
import fr.xcompta.core.base.exception.lettrage.LettrageMouvementDejaLettreException;
import fr.xcompta.core.base.exception.lettrage.LettrageMouvementDupliqueException;
import fr.xcompta.core.base.exception.lettrage.LettrageMouvementNonRattacheException;
import fr.xcompta.core.base.exception.lettrage.LettrageNbMouvementsInferieurADeuxException;
import fr.xcompta.core.base.exception.lettrage.LettrageNonEquilibreException;

/**
 * Lettrage comptable immuable de mouvements validés.
 *
 * <p>
 * La construction est atomique : la date, les mouvements, leur compte commun,
 * leur unicité, leur absence de lettrage préalable et leur somme nulle sont
 * vérifiés avant tout rattachement inverse. Une reconstruction incohérente
 * échoue par exception, sans modifier les mouvements fournis.
 * </p>
 *
 * <p>
 * L'identifiant vaut 0 avant la persistance ; une valeur strictement positive
 * ne peut être attribuée qu'une fois. Le délettrage complet relève du service
 * de persistance et doit être transactionnel.
 * </p>
 *
 * @author Fabrice Luneau
 */
public final class Lettrage implements ValidableMetier {

	private long id;
	private final LocalDate date;
	private final List<MouvementValidee> mouvements;

	/**
	 * Crée un nouveau lettrage à la date du jour.
	 *
	 * @param mouvements mouvements validés à rapprocher
	 * @throws XComptaCoreException si les règles métier ne sont pas respectées
	 */
	public Lettrage(final List<MouvementValidee> mouvements) throws XComptaCoreException {
		this(0, LocalDate.now(), mouvements);
	}

	/**
	 * Construit ou reconstruit un lettrage complet.
	 *
	 * @param id         identifiant positif en persistance, ou 0 avant persistance
	 * @param date       date du lettrage, non nulle
	 * @param mouvements liste complète des mouvements à lettrer
	 * @throws IllegalArgumentException si l'identifiant est négatif
	 * @throws XComptaCoreException     si le lettrage est incohérent
	 */
	public Lettrage(final long id, final LocalDate date, final List<MouvementValidee> mouvements)
			throws XComptaCoreException {
		if (id < 0) {
			throw new IllegalArgumentException("L'identifiant ne peut pas être négatif.");
		}
		if (date == null) {
			throw new XComptaCoreNullPointerException();
		}
		if (mouvements == null || mouvements.size() < 2) {
			throw new LettrageNbMouvementsInferieurADeuxException();
		}

		final List<MouvementValidee> copie = new ArrayList<>(mouvements);
		Compte compte = null;
		BigDecimal total = BigDecimal.ZERO;
		final java.util.Set<MouvementValidee> identites = Collections.newSetFromMap(new java.util.IdentityHashMap<>());
		for (MouvementValidee mouvement : copie) {
			if (mouvement == null) {
				throw new XComptaCoreNullPointerException(contexteErreur() + " : le mouvement ne peut pas être null.");
			}
			if (!identites.add(mouvement)) {
				throw new LettrageMouvementDupliqueException();
			}
			if (mouvement.estLettre()) {
				throw new LettrageMouvementDejaLettreException();
			}
			if (compte == null) {
				compte = mouvement.getCompte();
			} else if (!Objects.equals(compte, mouvement.getCompte())) {
				throw new LettrageCompteDifferentException();
			}
			total = total.add(mouvement.getMontant());
		}
		if (total.compareTo(BigDecimal.ZERO) != 0) {
			throw new LettrageNonEquilibreException();
		}

		this.id = id;
		this.date = date;
		this.mouvements = List.copyOf(copie);
		// Tous les contrôles ont réussi : les rattachements ne peuvent plus
		// échouer dans un usage mono-thread respectant le contrat métier.
		for (MouvementValidee mouvement : copie) {
			mouvement.lettrer(this);
		}
	}

	/** @return identifiant technique (0 avant persistance) */
	public long getId() {
		return id;
	}

	/** @return date du lettrage */
	public LocalDate getDate() {
		return date;
	}

	/** @return liste non modifiable des mouvements */
	public List<MouvementValidee> getMouvements() {
		return mouvements;
	}

	/**
	 * Attribue une seule fois l'identifiant fourni par la persistance.
	 *
	 * @param id identifiant strictement positif
	 * @throws IllegalArgumentException si id est inférieur ou égal à 0
	 * @throws IllegalStateException    si un identifiant est déjà attribué
	 */
	public void setId(final long id) {
		if (id <= 0) {
			throw new IllegalArgumentException("L'identifiant doit être positif.");
		}
		if (this.id != 0) {
			throw new IllegalStateException("L'identifiant est déjà attribué.");
		}
		this.id = id;
	}

	/**
	 * @return somme signée des mouvements, toujours nulle pour un lettrage valide
	 */
	public BigDecimal getTotal() {
		return mouvements.stream().map(MouvementValidee::getMontant).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/** @return toujours false pour un lettrage valide */
	public boolean isDebiteur() {
		return getTotal().signum() > 0;
	}

	/** @return toujours false pour un lettrage valide */
	public boolean isCrediteur() {
		return getTotal().signum() < 0;
	}

	/** @return true si le lettrage est équilibré */
	public boolean isEquilibre() {
		return getTotal().signum() == 0;
	}

	/**
	 * Vérifie la cohérence d'un lettrage existant, y compris les références
	 * inverses de ses mouvements.
	 *
	 * @throws XComptaCoreException si une règle métier est violée
	 */
	@Override
	public void testPreValidation() throws XComptaCoreException {
		if (date == null) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : la date ne peut pas être null.");
		}
		if (mouvements.size() < 2) {
			throw new LettrageNbMouvementsInferieurADeuxException();
		}
		final Compte compte = mouvements.get(0).getCompte();
		for (MouvementValidee mouvement : mouvements) {
			if (!Objects.equals(compte, mouvement.getCompte())) {
				throw new LettrageCompteDifferentException();
			}
			if (mouvement.getLettrage() != this) {
				throw new LettrageMouvementNonRattacheException();
			}
		}
		if (!isEquilibre()) {
			throw new LettrageNonEquilibreException();
		}
	}

	protected String contexteErreur() {
		return getClass().getSimpleName() + (getId() > 0 ? " [id=" + getId() + "]" : "");
	}
}