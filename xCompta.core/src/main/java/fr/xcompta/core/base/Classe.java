package fr.xcompta.core.base;

/**
 * Représente une classe du plan comptable.
 *
 * <p>
 * Une classe est identifiée par un numéro compris entre 1 et 9.
 * Son identité métier repose uniquement sur ce numéro.
 * </p>
 *
 * <p>
 * Les classes connues de XCompta sont accessibles par
 * {@link #getClasses()} et {@link #getClasse(byte)}.
 * </p>
 *
 * @param numero numéro de la classe, compris entre 1 et 9
 * @param libelle libellé de la classe
 *
 * @author Fabrice Luneau
 */
public record Classe(byte numero, String libelle) {

    /**
     * Classes du plan comptable connues de XCompta.
     */
    private static final Classe[] CLASSES = {
            new Classe((byte) 1, "Comptes de capitaux"),
            new Classe((byte) 2, "Comptes d'immobilisations"),
            new Classe((byte) 3, "Comptes de stocks et en-cours"),
            new Classe((byte) 4, "Comptes de tiers"),
            new Classe((byte) 5, "Comptes financiers"),
            new Classe((byte) 6, "Comptes de charges"),
            new Classe((byte) 7, "Comptes de produits"),
            new Classe((byte) 8, "Comptes spéciaux"),
            new Classe((byte) 9, "Comptes analytiques")
    };

    /**
     * Construit une classe et vérifie la validité de ses attributs.
     *
     * @param numero numéro de la classe
     * @param libelle libellé de la classe
     * @throws IllegalArgumentException si le numéro ou le libellé est invalide
     */
    public Classe {
        if (numero < 1 || numero > 9) {
            throw new IllegalArgumentException(
                    "Le numéro de classe doit être compris entre 1 et 9.");
        }

        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException(
                    "Le libellé d'une classe ne peut pas être null ou vide.");
        }

        libelle = libelle.trim();
    }

    /**
     * Retourne les classes du plan comptable connues de XCompta.
     *
     * <p>
     * Le tableau retourné est une copie du tableau interne afin d'empêcher
     * sa modification depuis l'extérieur de la classe.
     * </p>
     *
     * @return tableau des classes comptables
     */
    public static Classe[] getClasses() {
        return CLASSES.clone();
    }

    /**
     * Recherche une classe comptable par son numéro dans le référentiel.
     *
     * @param numero numéro de classe recherché, entre 1 et 9
     * @return classe correspondante ou {@code null} si le numéro est inconnu
     */
    public static Classe getClasse(final byte numero) {
        if (numero < 1 || numero > CLASSES.length) {
            return null;
        }
        return CLASSES[numero - 1];
    }

    /**
     * Retourne une représentation textuelle de la classe.
     *
     * @return numéro et libellé de la classe
     */
    @Override
    public String toString() {
        return numero + " " + libelle;
    }

    /**
     * Calcule le code de hachage à partir du numéro de classe.
     *
     * @return code de hachage
     */
    @Override
    public int hashCode() {
        return Byte.hashCode(numero);
    }

    /**
     * Compare deux classes selon leur numéro comptable.
     *
     * @param obj objet à comparer
     * @return {@code true} si les deux classes ont le même numéro
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Classe other)) {
            return false;
        }
        return numero == other.numero;
    }
}