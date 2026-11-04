package fr.xcompta.core.base;

import java.util.Arrays;
import java.util.Objects;

/**
 * Représente une catégorie de comptes du Plan Comptable Général (PCG).
 *
 * <p>
 * Une catégorie est identifiée par un numéro composé exactement de deux
 * chiffres. Le premier chiffre de son numéro correspond au numéro de la
 * classe comptable dont elle dépend dans la hiérarchie du plan comptable.
 * </p>
 *
 * <p>
 * Il n'existe volontairement aucune association entre un objet
 * {@link CategorieCompte} et un objet {@link Classe}. La classe d'une
 * catégorie est déduite directement de son numéro.
 * </p>
 *
 * <p>
 * Les catégories connues de XCompta peuvent être récupérées dans leur
 * ensemble ou filtrées par numéro de classe ou par objet {@link Classe}.
 * Cette organisation permet notamment de construire ultérieurement une
 * représentation hiérarchique du plan comptable dans un {@code JTree}.
 * </p>
 *
 * @param numero numéro de la catégorie, compris entre 10 et 99
 * @param libelle libellé de la catégorie
 *
 * @author Fabrice Luneau
 */
public record CategorieCompte(byte numero, String libelle) {

    /**
     * Catégories de comptes connues de XCompta.
     */
    private static final CategorieCompte[] CATEGORIES = {
            new CategorieCompte((byte) 10, "Capital et réserves"),
            new CategorieCompte((byte) 11, "Report à nouveau"),
            new CategorieCompte((byte) 12, "Résultat de l'exercice"),
            new CategorieCompte((byte) 13, "Subventions d'investissement"),
            new CategorieCompte((byte) 14, "Provisions réglementées"),
            new CategorieCompte((byte) 15, "Provisions"),
            new CategorieCompte((byte) 16, "Emprunts et dettes assimilées"),
            new CategorieCompte((byte) 17, "Dettes rattachées à des participations"),
            new CategorieCompte((byte) 18, "Comptes de liaison des établissements et sociétés en participation"),

            new CategorieCompte((byte) 20, "Immobilisations incorporelles"),
            new CategorieCompte((byte) 21, "Immobilisations corporelles"),
            new CategorieCompte((byte) 22, "Immobilisations mises en concession"),
            new CategorieCompte((byte) 23, "Immobilisations en cours"),
            new CategorieCompte((byte) 25, "Parts dans des entreprises liées et créances sur des entreprises liées"),
            new CategorieCompte((byte) 26, "Participations et créances rattachées à des participations"),
            new CategorieCompte((byte) 27, "Autres immobilisations financières"),
            new CategorieCompte((byte) 28, "Amortissements des immobilisations"),
            new CategorieCompte((byte) 29, "Dépréciations des immobilisations"),

            new CategorieCompte((byte) 31, "Matières premières et fournitures"),
            new CategorieCompte((byte) 32, "Autres approvisionnements"),
            new CategorieCompte((byte) 33, "En-cours de production de biens"),
            new CategorieCompte((byte) 34, "En-cours de production de services"),
            new CategorieCompte((byte) 35, "Stocks de produits"),
            new CategorieCompte((byte) 36, "Stocks provenant d'immobilisations"),
            new CategorieCompte((byte) 37, "Stocks de marchandises"),
            new CategorieCompte((byte) 38, "Stocks en voie d'acheminement, mis en dépôt ou donnés en consignation"),
            new CategorieCompte((byte) 39, "Dépréciations des stocks et en-cours"),

            new CategorieCompte((byte) 40, "Fournisseurs et comptes rattachés"),
            new CategorieCompte((byte) 41, "Clients et comptes rattachés"),
            new CategorieCompte((byte) 42, "Personnel et comptes rattachés"),
            new CategorieCompte((byte) 43, "Sécurité sociale et autres organismes sociaux"),
            new CategorieCompte((byte) 44, "État et autres collectivités publiques"),
            new CategorieCompte((byte) 45, "Groupe et associés"),
            new CategorieCompte((byte) 46, "Débiteurs divers et créditeurs divers"),
            new CategorieCompte((byte) 47, "Comptes transitoires ou d'attente"),
            new CategorieCompte((byte) 48, "Comptes de régularisation"),
            new CategorieCompte((byte) 49, "Dépréciations des comptes de tiers"),

            new CategorieCompte((byte) 50, "Valeurs mobilières de placement"),
            new CategorieCompte((byte) 51, "Banques, établissements financiers et assimilés"),
            new CategorieCompte((byte) 52, "Instruments financiers à terme"),
            new CategorieCompte((byte) 53, "Caisse"),
            new CategorieCompte((byte) 54, "Régies d'avances et accréditifs"),
            new CategorieCompte((byte) 58, "Virements internes"),
            new CategorieCompte((byte) 59, "Dépréciations des comptes financiers"),

            new CategorieCompte((byte) 60, "Achats"),
            new CategorieCompte((byte) 61, "Services extérieurs"),
            new CategorieCompte((byte) 62, "Autres services extérieurs"),
            new CategorieCompte((byte) 63, "Impôts, taxes et versements assimilés"),
            new CategorieCompte((byte) 64, "Charges de personnel"),
            new CategorieCompte((byte) 65, "Autres charges de gestion courante"),
            new CategorieCompte((byte) 66, "Charges financières"),
            new CategorieCompte((byte) 67, "Charges exceptionnelles"),
            new CategorieCompte((byte) 68, "Dotations aux amortissements, dépréciations et provisions"),
            new CategorieCompte((byte) 69, "Participation des salariés et impôts sur les bénéfices"),

            new CategorieCompte((byte) 70, "Ventes de produits fabriqués, prestations de services, marchandises"),
            new CategorieCompte((byte) 71, "Production stockée"),
            new CategorieCompte((byte) 72, "Production immobilisée"),
            new CategorieCompte((byte) 74, "Subventions d'exploitation"),
            new CategorieCompte((byte) 75, "Autres produits de gestion courante"),
            new CategorieCompte((byte) 76, "Produits financiers"),
            new CategorieCompte((byte) 77, "Produits exceptionnels"),
            new CategorieCompte((byte) 78, "Reprises sur amortissements, dépréciations et provisions"),
            new CategorieCompte((byte) 79, "Transferts de charges"),

            new CategorieCompte((byte) 80, "Engagements"),
            new CategorieCompte((byte) 88, "Résultat en instance d'affectation"),
            new CategorieCompte((byte) 89, "Bilan")
    };

    /**
     * Construit une catégorie de comptes.
     *
     * @param numero numéro de la catégorie
     * @param libelle libellé de la catégorie
     * @throws IllegalArgumentException si le numéro ne comporte pas exactement
     *         deux chiffres ou si le libellé est vide
     * @throws NullPointerException si le libellé est {@code null}
     */
    public CategorieCompte {
        if (numero < 10 || numero > 99) {
            throw new IllegalArgumentException(
                    "Le numéro d'une catégorie de comptes doit comporter "
                            + "exactement deux chiffres.");
        }

        Objects.requireNonNull(
                libelle,
                "Le libellé d'une catégorie de comptes ne peut pas être null.");

        libelle = libelle.trim();

        if (libelle.isEmpty()) {
            throw new IllegalArgumentException(
                    "Le libellé d'une catégorie de comptes ne peut pas être vide.");
        }
    }

    /**
     * Retourne le numéro de la classe correspondant à cette catégorie.
     *
     * <p>
     * Le numéro de classe est déduit du premier chiffre du numéro de la
     * catégorie. Aucune association avec un objet {@link Classe} n'est
     * nécessaire.
     * </p>
     *
     * @return numéro de la classe comptable
     */
    public byte getNumeroClasse() {
        return (byte) (numero / 10);
    }

    /**
     * Retourne toutes les catégories de comptes connues de XCompta.
     *
     * <p>
     * Le tableau retourné est une copie du tableau interne afin d'empêcher
     * sa modification depuis l'extérieur de la classe.
     * </p>
     *
     * @return tableau de toutes les catégories de comptes
     */
    public static CategorieCompte[] getCategories() {
        return CATEGORIES.clone();
    }

    /**
     * Retourne les catégories correspondant à un numéro de classe.
     *
     * <p>
     * Par exemple, {@code getCategories(1)} retourne les catégories
     * 10, 11, 12, 13, etc.
     * </p>
     *
     * @param numeroClasse numéro de la classe recherchée
     * @return nouveau tableau des catégories appartenant à cette classe,
     *         éventuellement vide si aucune catégorie ne correspond
     * @throws IllegalArgumentException si le numéro de classe n'est pas
     *         compris entre 1 et 9
     */
    public static CategorieCompte[] getCategories(final int numeroClasse) {
        if (numeroClasse < 1 || numeroClasse > 9) {
            throw new IllegalArgumentException(
                    "Le numéro de classe doit être compris entre 1 et 9.");
        }

        return Arrays.stream(CATEGORIES)
                .filter(categorie ->
                        categorie.getNumeroClasse() == numeroClasse)
                .toArray(CategorieCompte[]::new);
    }

    /**
     * Retourne les catégories correspondant à une classe.
     *
     * <p>
     * Cette méthode est une méthode de confort. Elle ne crée aucune
     * association entre {@link Classe} et {@link CategorieCompte}.
     * La recherche repose uniquement sur le numéro de la classe.
     * </p>
     *
     * @param classe classe recherchée
     * @return nouveau tableau des catégories appartenant à cette classe,
     *         éventuellement vide si aucune catégorie ne correspond
     * @throws NullPointerException si la classe est {@code null}
     */
    public static CategorieCompte[] getCategories(final Classe classe) {
        Objects.requireNonNull(
                classe,
                "La classe comptable ne peut pas être null.");

        return getCategories(classe.numero());
    }

    /**
     * Calcule le code de hachage à partir du seul numéro de catégorie.
     * Le libellé ne participe pas au calcul.
     *
     * @return code de hachage
     */
    @Override
    public int hashCode() {
        return Byte.hashCode(numero);
    }

    /**
     * Compare deux catégories selon leur numéro.
     * Deux catégories de même numéro sont égales, même si leurs libellés diffèrent.
     *
     * @param obj objet à comparer
     * @return {@code true} si les deux catégories ont le même numéro
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CategorieCompte other)) {
            return false;
        }
        return numero == other.numero;
    }
}