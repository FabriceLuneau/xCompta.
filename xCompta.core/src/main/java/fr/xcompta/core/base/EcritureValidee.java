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
 * {@link EcritureDeBrouillard}, son contenu comptable est immuable. Ses identifiants peuvent être
 * complétés une seule fois lors de la persistance, sans remplacer les
 * identifiants déjà attribués.
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
 * sont également copiés.
 * </p>
 *
 * <p>
 * Un {@link MouvementValidee} peut être reconstruit temporairement sous forme
 * détachée, notamment à partir des données de persistance. Lors de la
 * construction de l'écriture validée, chaque copie de mouvement est rattachée à
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
    /** Numéro définitif ; zéro signifie non attribué. */
    private int numeroEnregistrement;

    /**
     * Construit une nouvelle écriture validée, sans numéros attribués.
     * Les mouvements sont copiés avec des identifiants techniques à zéro.
     * @param libelle libellé comptable
     * @param date date comptable
     * @param journal journal comptable
     * @param mouvements mouvements sources
     * @throws XComptaCoreException si une règle métier est violée
     */
    public EcritureValidee(final String libelle, final LocalDate date,
            final Journal journal, final List<MouvementValidee> mouvements)
            throws XComptaCoreException {
        super(0, 0, libelle, date, journal, mouvements,
                source -> new MouvementValidee(0, source.getMontant(), source.getCompte()));
        this.numeroEnregistrement = 0;
        testCoherenceIdentifiants();
        testPostConstruction();
    }

    /**
     * Construit ou reconstruit une écriture validée avec ses identifiants.
     * @param id identifiant technique (0 avant persistance)
     * @param numeroDePreEnregistrement numéro métier (0 si non attribué)
     * @param numeroEnregistrement numéro définitif (0 si non attribué)
     * @param libelle libellé comptable
     * @param date date comptable
     * @param journal journal comptable
     * @param mouvements mouvements sources copiés avec leurs identifiants
     * @throws XComptaCoreException si une règle métier est violée
     * @throws IllegalArgumentException si les identifiants sont incohérents
     */
    public EcritureValidee(final int id, final int numeroDePreEnregistrement,
            final int numeroEnregistrement, final String libelle, final LocalDate date,
            final Journal journal, final List<MouvementValidee> mouvements)
            throws XComptaCoreException {
        super(id, numeroDePreEnregistrement, libelle, date, journal, mouvements,
                source -> new MouvementValidee(source.getId(), source.getMontant(), source.getCompte()));
        this.numeroEnregistrement = numeroEnregistrement;
        testCoherenceIdentifiants();
        testPostConstruction();
    }

    /**
     * Vérifie la cohérence des identifiants, indépendamment du contenu comptable.
     */
    private void testCoherenceIdentifiants() {
        if (id < 0 || numeroDePreEnregistrement < 0 || numeroEnregistrement < 0) {
            throw new IllegalArgumentException("Les identifiants ne peuvent pas être négatifs.");
        }
        if (id > 0 && numeroDePreEnregistrement == 0) {
            throw new IllegalArgumentException("Numéro de préenregistrement manquant.");
        }
        if (numeroEnregistrement > 0 && (id == 0 || numeroDePreEnregistrement == 0)) {
            throw new IllegalArgumentException("Numéro d'enregistrement incohérent.");
        }
    }

    /** @return numéro définitif, ou zéro s'il n'est pas attribué */
    public int getNumeroEnregistrement() {
        return numeroEnregistrement;
    }
    
    /**
     * Attribue le numéro d'enregistrement définitif à une écriture
     * dont l'identifiant technique et le numéro de préenregistrement
     * ont déjà été attribués.
     *
     * @param numeroEnregistrement numéro d'enregistrement définitif
     * @throws IllegalArgumentException si les identifiants ne sont pas
     *         strictement positifs
     * @throws IllegalStateException si l'écriture possède déjà un
     *         numéro d'enregistrement définitif
     */
    public void setIdentifiants(final int numeroEnregistrement) {
        setIdentifiants(
                this.id,
                this.numeroDePreEnregistrement,
                numeroEnregistrement);
    }

    /**
     * Complète les identifiants après la persistance de l'écriture.
     * Les identifiants déjà attribués doivent être conservés.
     * Aucun attribut n'est modifié si un contrôle échoue.
     *
     * @param id identifiant technique strictement positif
     * @param numeroDePreEnregistrement numéro de préenregistrement strictement positif
     * @param numeroEnregistrement numéro définitif strictement positif
     * @throws IllegalArgumentException si un argument est invalide ou incompatible
     * @throws IllegalStateException si le numéro définitif est déjà attribué
     */
    public void setIdentifiants(final int id, final int numeroDePreEnregistrement,
            final int numeroEnregistrement) {
        if (this.numeroEnregistrement != 0) {
            throw new IllegalStateException("L'écriture est déjà définitivement enregistrée.");
        }
        if (id <= 0 || numeroDePreEnregistrement <= 0 || numeroEnregistrement <= 0) {
            throw new IllegalArgumentException("Les trois identifiants doivent être strictement positifs.");
        }
        if (this.id != 0 && this.id != id) {
            throw new IllegalArgumentException("L'identifiant technique déjà attribué doit être conservé.");
        }
        if (this.numeroDePreEnregistrement != 0
                && this.numeroDePreEnregistrement != numeroDePreEnregistrement) {
            throw new IllegalArgumentException("Le numéro de préenregistrement déjà attribué doit être conservé.");
        }
        this.id = id;
        this.numeroDePreEnregistrement = numeroDePreEnregistrement;
        this.numeroEnregistrement = numeroEnregistrement;
    }

	/**
	 * Vérifie la cohérence de l'écriture immédiatement après sa construction.
	 *
	 * @throws XComptaCoreException si une règle métier n'est pas respectée
	 */
	@Override
	protected void testPostConstruction() throws XComptaCoreException {

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
	
    /** Égalité complète des champs, hors rattachement parent déjà implicite. */
    @Override
    public boolean equals(final Object objet) {
        if (this == objet) return true;
        if (!(objet instanceof EcritureValidee autre)) return false;
        if (getId() != autre.getId()
                || getNumeroDePreEnregistrement() != autre.getNumeroDePreEnregistrement()
                || numeroEnregistrement != autre.numeroEnregistrement
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
        int resultat = Objects.hash(getId(), getNumeroDePreEnregistrement(), numeroEnregistrement, getLibelle(), getDate(), getJournal());
        for (MouvementValidee mouvement : getMouvements()) {
            BigDecimal montant = mouvement.getMontant();
            resultat = 31 * resultat + Objects.hash(mouvement.getId(), mouvement.getCompte(),
                    montant == null ? null : montant.stripTrailingZeros(),
                    System.identityHashCode(mouvement.getLettrage()));
        }
        return resultat;
    }
}
