package fr.xcompta.core.base;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Contrat de vérification de la cohérence des objets métier complexes.
 *
 * <p>
 * Permet de contrôler qu'un objet respecte ses règles métier avant
 * sa persistance, notamment pour les écritures et les mouvements
 * comptables.
 * </p>
 *
 * <p>
 * Cette interface ne réalise aucune opération de persistance.
 * </p>
 */
public interface ValidableMetier {

    /**
     * Vérifie que l'état courant de l'objet respecte ses règles métier.
     *
     * @throws XComptaCoreException si une règle métier n'est pas respectée
     */
    void testPreValidation() throws XComptaCoreException;

    /**
     * Indique si l'état courant de l'objet respecte ses règles métier.
     *
     * <p>
     * Cette méthode est un raccourci de {@link #testPreValidation()}.
     * Elle retourne {@code false} lorsqu'une exception métier ou une
     * {@link IllegalArgumentException} est levée.
     * </p>
     *
     * @return {@code true} si les contrôles réussissent,
     *         {@code false} sinon
     */
    default boolean estValide() {
        try {
            testPreValidation();
            return true;
        } catch (XComptaCoreException | IllegalArgumentException exception) {
            return false;
        }
    }
}
