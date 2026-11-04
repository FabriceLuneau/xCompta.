package fr.xcompta.core.base;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.List;

import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.ecriture.EcritureMontantTotalAZeroException;
import fr.xcompta.core.base.exception.ecriture.EcritureNbMouvementsInferieurADeuxException;
import fr.xcompta.core.base.exception.ecriture.EcritureNonEquilibreeException;
import fr.xcompta.core.date.exception.DateInvalideException;

/**
 * Représente une écriture comptable validée.
 *
 * <p>
 * Une écriture validée représente une écriture comptable définitive. Contrairement à une
 * {@link EcritureDeBrouillard}, elle ne propose aucune méthode publique
 * permettant de modifier son identifiant, son libellé, sa date, son journal ou
 * sa collection de mouvements.
 * </p>
 *
 * <p>
 * Une écriture validée doit être cohérente dès sa construction. Elle doit
 * comporter au moins deux mouvements, être équilibrée et chacun de ses
 * mouvements doit posséder un compte ainsi qu'un montant non nul et différent
 * de zéro.
 * </p>
 *
 * <h2>Mouvements et reconstruction</h2>
 *
 * <p>
 * La classe mère effectue une copie de la collection transmise au
 * constructeur : une modification ultérieure de la liste d'origine ne modifie
 * donc pas la collection interne de l'écriture. Les objets mouvements eux-mêmes
 * ne sont pas copiés.
 * </p>
 *
 * <p>
 * Un {@link MouvementValidee} peut être reconstruit temporairement sous forme
 * détachée, notamment à partir des données de persistance. Lors de la
 * construction de l'écriture validée, chaque mouvement fourni est rattaché à
 * l'instance créée avant l'exécution des contrôles métier et référentiels.
 * </p>
 *
 * <p>
 * Cette classe appartient au noyau métier de XCompta et ne contient aucune
 * annotation de persistance. Les contraintes propres à JPA ou Hibernate doivent
 * être portées par le module d'adaptation chargé de la persistance.
 * </p>
 *
 * @author Fabrice Luneau
 * @since 1.0
 */
public final class EcritureValidee extends Ecriture<MouvementValidee> {
	/* Numérotation définitive prévue pour une version ultérieure. */
	// private int numeroEnregistrement;

	/**
	 * Construit une écriture validée à partir de toutes ses valeurs.
	 *
	 * @param id         identifiant technique de l'écriture validée
	 * @param date       date comptable
	 * @param journal    journal comptable
	 * @param libelle    libellé de l'écriture
	 * @param mouvements mouvements fournis pour reconstruire l'écriture
	 * @throws XComptaCoreException     si une règle métier n'est pas respectée
	 * @throws IllegalArgumentException si l'identifiant est négatif
	 */
	public EcritureValidee(final int id, final String libelle, final LocalDate date, final Journal journal,
			final List<MouvementValidee> mouvements) throws XComptaCoreException {

		super(id, libelle, date, journal, mouvements);
	    
		testPostConstruction();
	}

	/**
	 * Vérifie la cohérence de l'écriture immédiatement après sa construction.
	 *
	 * @throws XComptaCoreException si une règle métier n'est pas respectée
	 */
	@Override
	protected void testPostConstruction() throws XComptaCoreException {
// id == 0 : écriture validée non encore persistée.
		// Un identifiant négatif est interdit.
		if (getId() < 0) {
			throw new IllegalArgumentException("L'identifiant d'une écriture validée ne peut pas être négatif.");
		}

		if (getDate() == null) {
			throw new DateInvalideException(contexteErreur() + "La date de l'écriture validée ne peut pas être null.");
		}

		if (getJournal() == null) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : le journal ne peut pas être null.");
		}

		if (getLibelle() == null || getLibelle().isBlank()) {
			throw new XComptaCoreNullPointerException(contexteErreur() + " : le libellé ne peut pas être null ou vide.");
		}

		if (size() < 2) {
			throw new EcritureNbMouvementsInferieurADeuxException(contexteErreur() + 
					"Une écriture validée doit comporter au moins deux mouvements.");
		}

		if (!estEquilibree()) {
			throw new EcritureNonEquilibreeException(contexteErreur() + "L'écriture validée n'est pas équilibrée.");
		}

		if (getTotalDebit().signum() <= 0) {
			throw new EcritureMontantTotalAZeroException(contexteErreur() + 
					"Le montant total d'une écriture doit être strictement positif.");
		}
		
		testIntegriteDesMouvements();

		for (MouvementValidee mouvement : getMouvements()) {
			mouvement.testPreValidation();
		}
		
		testNonCompensation();

		// TODO métier : vérifier que la date appartient à l'exercice comptable.
	}

	/**
	 * Vérifie que l'écriture satisfait les règles nécessaires à sa validation.
	 *
	 * <p>
	 * Pour une écriture déjà validée, les règles sont identiques à celles
	 * contrôlées après la construction.
	 * </p>
	 *
	 * @throws XComptaCoreException si une règle métier n'est pas respectée
	 */
	@Override
	public void testPreValidation() throws XComptaCoreException {
		// Pour une écriture validée, post-construction et pré-validation appliquent les mêmes règles.
		testPostConstruction();
	}
	
	/*
	 * Numérotation définitive mise en attente (code conservé en commentaire).
	 *
	 * public int getNumeroEnregistrement() {
	 *     return numeroEnregistrement;
	 * }
	 *
	 * public void setNumeroEnregistrement(final int numeroEnregistrement) {
	 *     if (numeroEnregistrement <= 0) {
	 *         throw new IllegalArgumentException("Le numéro doit être strictement positif.");
	 *     }
	 *     if (this.numeroEnregistrement != 0) {
	 *         throw new IllegalStateException("Numéro déjà attribué.");
	 *     }
	 *     this.numeroEnregistrement = numeroEnregistrement;
	 * }
	 */

    /** Égalité complète des champs, hors rattachement parent déjà implicite. */
    @Override
    public boolean equals(final Object objet) {
        if (this == objet) return true;
        if (!(objet instanceof EcritureValidee autre)) return false;
        if (getId() != autre.getId()
                || !Objects.equals(getLibelle(), autre.getLibelle())
                || !Objects.equals(getDate(), autre.getDate())
                || !Objects.equals(getJournal(), autre.getJournal())
                || size() != autre.size()) return false;
        for (int i = 0; i < size(); i++) {
            MouvementValidee a = getMouvements().get(i);
            MouvementValidee b = autre.getMouvements().get(i);
            if (a.getId() != b.getId()
                    || !Objects.equals(a.getCompte(), b.getCompte())
                    || !memeMontant(a.getMontant(), b.getMontant())
                    || a.getLettrage() != b.getLettrage()) return false;
        }
        return true;
    }

    private static boolean memeMontant(BigDecimal a, BigDecimal b) {
        return a == b || (a != null && b != null && a.compareTo(b) == 0);
    }

    @Override
    public int hashCode() {
        int resultat = Objects.hash(getId(), getLibelle(), getDate(), getJournal());
        for (MouvementValidee mouvement : getMouvements()) {
            BigDecimal montant = mouvement.getMontant();
            resultat = 31 * resultat + Objects.hash(mouvement.getId(), mouvement.getCompte(),
                    montant == null ? null : montant.stripTrailingZeros(),
                    System.identityHashCode(mouvement.getLettrage()));
        }
        return resultat;
    }
}
