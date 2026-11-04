package fr.xcompta.core.base;

import java.util.Objects;

/**
 * Représente un journal comptable identifié par son code métier.
 *
 * <p>
 * Dans une même comptabilité, le code d'un journal est unique. Deux journaux
 * possédant le même code sont considérés comme égaux, même si leurs libellés
 * diffèrent. Le libellé est une information descriptive qui ne participe pas
 * à l'identité du journal.
 * </p>
 *
 * <p>
 * L'unicité du code au sein d'une comptabilité doit être garantie par la
 * couche de persistance. Entre comptabilités différentes, un même code ne
 * garantit pas que les journaux ont la même signification : leur rapprochement
 * nécessite une correspondance explicite.
 * </p>
 *
 * @param code code métier identifiant le journal dans une comptabilité
 * @param libelle libellé descriptif du journal
 *
 * @author Fabrice Luneau
 */
public record Journal(String code, String libelle) {

    /**
     * Construit un journal après validation de son code et de son libellé.
     * Les espaces en début et en fin de chaque valeur sont supprimés.
     *
     * @param code code du journal
     * @param libelle libellé du journal
     * @throws NullPointerException si le code ou le libellé est {@code null}
     * @throws IllegalArgumentException si le code ou le libellé est vide
     *         ou composé uniquement d'espaces
     */
    public Journal {
        if (code == null) {
            throw new NullPointerException(
                    "Le code du journal ne peut pas être null.");
        }

        if (code.isBlank()) {
            throw new IllegalArgumentException(
                    "Le code du journal ne peut pas être vide.");
        }

        if (libelle == null) {
            throw new NullPointerException(
                    "Le libellé du journal ne peut pas être null.");
        }

        if (libelle.isBlank()) {
            throw new IllegalArgumentException(
                    "Le libellé du journal ne peut pas être vide.");
        }

        code = code.trim();
        libelle = libelle.trim();
    }

    /**
     * Retourne une représentation textuelle du journal.
     *
     * @return code suivi d'un espace et du libellé
     */
    @Override
    public String toString() {
        return code + " " + libelle;
    }

    /**
     * Calcule le code de hachage à partir du seul code métier.
     *
     * @return code de hachage du code du journal
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    /**
     * Compare deux journaux selon leur code métier uniquement.
     * Le libellé ne participe pas à l'égalité.
     *
     * @param objet objet à comparer
     * @return {@code true} si l'objet est un journal de même code
     */
    @Override
    public boolean equals(final Object objet) {
        if (this == objet) {
            return true;
        }
        if (!(objet instanceof Journal autre)) {
            return false;
        }
        return code.equals(autre.code);
    }
}
