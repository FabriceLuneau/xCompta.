package fr.xcompta.core.base;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreIllegalStateException;
import fr.xcompta.core.base.exception.ecriture.EcritureCompenseException;
import fr.xcompta.core.base.exception.ecriture.EcritureMouvementViolationContrainteReferentielleException;

/**
 * Classe métier abstraite représentant une écriture comptable.
 *
 * <p>
 * Cette classe factorise les propriétés et les comportements communs aux
 * écritures de brouillard et aux écritures validées.
 * </p>
 *
 * <p>
 * Elle appartient au noyau métier de XCompta et ne dépend d'aucune technologie
 * de persistance.
 * </p>
 *
 * @param <M> type de mouvement autorisé dans cette écriture
 * @author Fabrice Luneau
 * @since 1.0
 */
public abstract class Ecriture<M extends Mouvement> implements Iterable<M>, ValidableMetier {
	/** Identifiant technique de l'écriture. */
	int id; // État commun : modifiable uniquement par EcritureDeBrouillard.

	/** Date comptable de l'écriture. */
	LocalDate date = LocalDate.now();

	/** Libellé de l'écriture. */
	String libelle;

	/** Journal auquel appartient l'écriture. */
	Journal journal;

	/**
	 * Collection interne des mouvements.
	 *
	 * <p>
	 * La référence de cette collection n'est jamais remplacée.
	 * </p>
	 */
	private final List<M> mouvements = new ArrayList<>();

	/**
	 * Construit une écriture comptable.
	 *
	 * <p>La liste initiale peut être {@code null} ou vide. Les références des
	 * mouvements sont copiées dans une collection interne indépendante ; les
	 * mouvements eux-mêmes ne sont pas dupliqués.</p>
	 *
	 * <p>Avant tout rattachement, le constructeur vérifie que la liste ne
	 * contient aucun élément {@code null} et qu'aucun mouvement n'appartient
	 * déjà à une autre écriture, ni deux fois la même instance. Cette vérification préalable évite de rattacher
	 * partiellement les mouvements lorsqu'une entrée est invalide.</p>
	 *
	 * <p>Les contrôles propres au brouillard et aux écritures validées sont
	 * assurés par les sous-classes.</p>
	 *
	 * @param id identifiant technique
	 * @param libelle libellé comptable
	 * @param date date comptable
	 * @param journal journal comptable
	 * @param mouvementsSource mouvements initiaux, éventuellement {@code null}
	 * @throws NullPointerException si un mouvement de la liste est {@code null}
	 * @throws IllegalArgumentException si la liste contient deux fois la même instance
	 * @throws EcritureMouvementViolationContrainteReferentielleException
	 *         si un mouvement appartient déjà à une autre écriture
	 */
	protected Ecriture(int id, String libelle, LocalDate date, Journal journal,
			List<? extends M> mouvementsSource)
			throws EcritureMouvementViolationContrainteReferentielleException {
		this.id = id;
		this.libelle = libelle;
		this.date = date;
		this.journal = journal;

		// Une liste absente équivaut à une liste initiale vide.
		if (mouvementsSource == null) {
			return;
		}

		// Détecte les doublons par identité, indépendamment de equals/hashCode.
		Set<Mouvement> mouvementsVus =
				Collections.newSetFromMap(new IdentityHashMap<>());

		// Premier passage : aucun mouvement n'est modifié.
		for (M mouvement : mouvementsSource) {
			Objects.requireNonNull(mouvement,
					"La liste ne doit pas contenir de mouvement null.");
			if (!mouvementsVus.add(mouvement)) {
				throw new IllegalArgumentException(
						"Une même instance de mouvement figure plusieurs fois dans la liste.");
			}
			if (mouvement.getEcriture() != null && mouvement.getEcriture() != this) {
				throw new EcritureMouvementViolationContrainteReferentielleException(
						"Impossible de rattacher un mouvement déjà attaché à une autre écriture.");
			}
		}

		// Deuxième passage : rattachement et copie des références.
		for (M mouvement : mouvementsSource) {
			mouvement.rattacherEcriture(this);
			this.mouvements.add(mouvement);
		}
	}

	/**
	 * Retourne l'identifiant technique.
	 *
	 * @return identifiant technique
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retourne le libellé.
	 *
	 * @return libellé de l'écriture
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Retourne la date comptable.
	 *
	 * @return date comptable
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Retourne le journal.
	 *
	 * @return journal comptable
	 */
	public Journal getJournal() {
		return journal;
	}

	/**
	 * Retourne une liste non modifiable contenant les mouvements de l'écriture. Les
	 * mouvements eux-mêmes ne sont pas dupliqués.
	 *
	 * @return liste non modifiable des mouvements de l'écriture
	 */
	public List<M> getMouvements() {
		return List.copyOf(mouvements);
	}

	/**
	 * Retourne la liste interne modifiable des mouvements.
	 *
	 * <p>
	 * Cette méthode est accessible aux sous-classes d'Écriture et ne doit jamais
	 * être exposée directement à l'extérieur. Le mot-clé {@code final} interdit sa
	 * redéfinition, mais ne rend pas la collection immuable.
	 *
	 * L'utilisation de cette méthode doit rester limitée aux opérations de
	 * construction et aux écritures de brouillard. Elle ne garantit pas à elle
	 * seule l'immuabilité des écritures validées.
	 * </p>
	 *
	 * @return la liste interne modifiable des mouvements
	 */
	protected final List<M> getMouvementsModifiables() {
		return mouvements;
	}

	/**
	 * Retourne le nombre de mouvements.
	 *
	 * @return nombre de mouvements
	 */
	public int size() {
		return mouvements.size();
	}

	/**
	 * Indique si l'écriture ne contient aucun mouvement.
	 *
	 * @return {@code true} si l'écriture est vide
	 */
	public boolean isEmpty() {
		return mouvements.isEmpty();
	}

	/**
	 * Calcule le total des montants débiteurs, représentés par des montants
	 * strictement positifs.
	 *
	 * @return total des débits
	 */
	public BigDecimal getTotalDebit() {
		BigDecimal totalDebit = BigDecimal.ZERO;

		for (Mouvement mouvement : mouvements) {
			if (mouvement.getMontant().signum() > 0) {
				totalDebit = totalDebit.add(mouvement.getMontant());
			}
		}

		return totalDebit;
	}

	/**
	 * Calcule le total des montants créditeurs, représentés par des montants
	 * strictement négatifs.
	 *
	 * <p>
	 * Le résultat conserve donc son signe négatif.
	 * </p>
	 *
	 * @return total signé des crédits
	 */
	public BigDecimal getTotalCredit() {
		BigDecimal totalCredit = BigDecimal.ZERO;

		for (Mouvement mouvement : mouvements) {
			if (mouvement.getMontant().signum() < 0) {
				totalCredit = totalCredit.add(mouvement.getMontant());
			}
		}

		return totalCredit;
	}

	/**
	 * Calcule le solde algébrique de l'écriture.
	 *
	 * @return somme des montants des mouvements
	 */
	public BigDecimal getSolde() {
		BigDecimal solde = BigDecimal.ZERO;

		for (Mouvement mouvement : mouvements) {
			solde = solde.add(mouvement.getMontant());
		}

		return solde;
	}

	/**
	 * Indique si l'écriture est équilibrée.
	 *
	 * @return {@code true} lorsque le solde est nul
	 */
	public boolean estEquilibree() {
		return getSolde().compareTo(BigDecimal.ZERO) == 0;
	}

	/**
	 * Génère une écriture de contrepassation.
	 *
	 * <p>
	 * La contrepassation est une nouvelle {@link EcritureDeBrouillard} qui reprend
	 * le journal et le libellé de l'écriture source. Chaque mouvement est dupliqué
	 * sur le même compte avec un montant de signe opposé. L'ordre des mouvements
	 * est conservé.
	 * </p>
	 *
	 * <p>
	 * La date de la contrepassation est initialisée à la date du jour et son
	 * identifiant reste celui d'une nouvelle écriture de brouillard.
	 * </p>
	 *
	 * @return nouvelle écriture de brouillard représentant la contrepassation
	 * @throws XComptaCoreException si un mouvement ne peut pas être dupliqué ou
	 *                              rattaché à l'écriture de contrepassation
	 */
	public EcritureDeBrouillard contrepasser() throws XComptaCoreException {

		if (mouvements.isEmpty()) {
			throw new XComptaCoreIllegalStateException(
					"La contrepassation est impossible : l'écriture source est vide.");
		}

		EcritureDeBrouillard ecritureDestination = new EcritureDeBrouillard();
		ecritureDestination.setDate(LocalDate.now());
		ecritureDestination.setJournal(this.journal);
		ecritureDestination.setLibelle("Contrepassation de :" + this.libelle);

		for (Mouvement mouvementSource : mouvements) {
			Objects.requireNonNull(mouvementSource, "Impossible de contrepasser un mouvement null.");

			MouvementDeBrouillard mouvementDestination = mouvementSource.dupliquer(ecritureDestination);
			mouvementDestination.setMontant(mouvementDestination.getMontant().negate());

			ecritureDestination.ajouterMouvementALaFin(mouvementDestination);
		}

		return ecritureDestination;
	}

	/**
	 * Retourne un itérateur qui ne permet pas de modifier la liste interne.
	 *
	 * @return itérateur sur les mouvements
	 */
	@Override
	public Iterator<M> iterator() {
		return getMouvements().iterator();
	}

	/**
	 * Retourne une représentation textuelle de l'écriture.
	 *
	 * @return représentation du journal et des mouvements de l'écriture
	 */
	@Override
	public String toString() {
		StringBuilder resultat = new StringBuilder();
		resultat.append('-').append(journal).append('\n');

		for (Mouvement mouvement : mouvements) {
			resultat.append(mouvement).append('\n');
		}

		return resultat.toString();
	}

	/**
	 * Vérifie les invariants applicables immédiatement après la construction.
	 *
	 * <p>
	 * Les sous-classes définissent le niveau de cohérence exigé à ce stade. Une
	 * écriture de brouillard peut notamment rester dans un état transitoire alors
	 * qu'une écriture validée doit être conforme dès sa construction.
	 * </p>
	 *
	 * @throws XComptaCoreException si une règle de cohérence propre à la
	 *                              sous-classe n'est pas respectée
	 */
	protected abstract void testPostConstruction() throws XComptaCoreException;

	/**
	 * Vérifie que chaque mouvement de l'écriture référence bien cette écriture.
	 * 
	 * @throws EcritureMouvementViolationContrainteReferentielleException si le
	 *                                                                    rattachement
	 *                                                                    est
	 *                                                                    invalide
	 */
	protected void testIntegriteDesMouvements() throws EcritureMouvementViolationContrainteReferentielleException {
		for (Mouvement mouvement : mouvements) {
			verifierContrainteReferentielleUnMouvement(mouvement);
		}
	}

	private void verifierContrainteReferentielleUnMouvement(Mouvement mouvement)
			throws EcritureMouvementViolationContrainteReferentielleException {
		if (mouvement.getEcriture() != this) {
			throw new EcritureMouvementViolationContrainteReferentielleException();
		}

	}

	/**
	 * Vérifie qu'un même compte n'est pas utilisé par plusieurs mouvements de
	 * l'écriture.
	 * 
	 */
	public void testNonCompensation() throws EcritureCompenseException {
		for (int i = 0; i < mouvements.size() - 1; i++) {
			Mouvement mouvement1 = mouvements.get(i);

			for (int j = i + 1; j < mouvements.size(); j++) {
				Mouvement mouvement2 = mouvements.get(j);

				if (mouvement1.getCompte().equals(mouvement2.getCompte())) {
					throw new EcritureCompenseException();
				}
			}
		}
	}

	/**
	 * Indique si cette écriture est comptablement équivalente à une autre.
	 *
	 * <p>
	 * Deux écritures sont équivalentes lorsqu'elles comportent les mêmes mouvements
	 * comptables, c'est-à-dire les mêmes comptes associés aux mêmes montants
	 * signés, indépendamment de l'ordre des mouvements.
	 * </p>
	 *
	 * <p>
	 * La date, le journal, le libellé, l'identifiant de l'écriture ainsi que les
	 * identifiants des mouvements ne sont pas pris en compte.
	 * </p>
	 *
	 * <p>
	 * Limite actuelle : les mouvements répétés ne sont pas associés un à un. Cette
	 * méthode doit encore être corrigée sur ce point.
	 * </p>
	 *
	 * @param autre écriture de brouillard ou écriture validée à comparer
	 * @return {@code true} si les deux écritures sont comptablement équivalentes,
	 *         {@code false} sinon
	 */
	public boolean estEquivalente(final Ecriture<?> autre) {

		if (autre == null || size() != autre.size()) {
			return false;
		}

		return getMouvements().stream()
				.allMatch(mouvement -> autre.getMouvements().stream()
						.anyMatch(autreMouvement -> mouvement.getCompte().equals(autreMouvement.getCompte())
								&& mouvement.getMontant().compareTo(autreMouvement.getMontant()) == 0));
	}

	/**
	 * Indique si cette écriture est l'inverse comptable d'une autre.
	 *
	 * <p>
	 * Deux écritures sont inverses lorsqu'elles utilisent les mêmes comptes avec
	 * des montants exactement opposés, indépendamment de l'ordre des mouvements.
	 * </p>
	 *
	 * <p>
	 * La date, le journal, le libellé, l'identifiant de l'écriture ainsi que les
	 * identifiants des mouvements ne sont pas pris en compte.
	 * </p>
	 *
	 * <p>
	 * Limite actuelle : les mouvements répétés ne sont pas associés un à un. Cette
	 * méthode doit encore être corrigée sur ce point.
	 * </p>
	 *
	 * @param autre écriture à comparer
	 * @return {@code true} si l'autre écriture constitue l'inverse comptable de
	 *         cette écriture, {@code false} sinon
	 */
	public boolean estInverse(final Ecriture<?> autre) {

		if (autre == null || size() != autre.size()) {
			return false;
		}

		return getMouvements().stream()
				.allMatch(mouvement -> autre.getMouvements().stream()
						.anyMatch(autreMouvement -> mouvement.getCompte().equals(autreMouvement.getCompte())
								&& mouvement.getMontant().negate().compareTo(autreMouvement.getMontant()) == 0));
	}

	protected String contexteErreur() {
		return getClass().getSimpleName() + (getId() > 0 ? " [id=" + getId() + "]" : "");
	}

	/**
	 * Vérifie si une instance de mouvement est déjà présente dans l'écriture.
	 *
	 * La comparaison repose sur l'identité des objets (==) et non sur leur égalité
	 * métier (equals).
	 *
	 * @param mouvement mouvement recherché
	 * @return true si cette instance est présente, false sinon
	 */
	public boolean mouvementEstPresent(Mouvement mouvement) {
		for (Mouvement mouvementPresent : mouvements) {
			if (mouvementPresent == mouvement) {
				return true;
			}
		}
		return false;
	}
}