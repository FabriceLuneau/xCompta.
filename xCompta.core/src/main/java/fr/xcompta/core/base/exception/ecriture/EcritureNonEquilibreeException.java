package fr.xcompta.core.base.exception.ecriture;

/**
 * Exception levée lorsqu'une écriture comptable n'est pas équilibrée.
 *
 * <p>Une écriture est équilibrée lorsque le total des montants portés
 * au débit est égal au total des montants portés au crédit.</p>
 *
 * <p>Cette exception concerne uniquement l'équilibre entre les débits
 * et les crédits. Les autres règles de validité de l'écriture, notamment
 * le nombre de mouvements et les montants nuls, sont contrôlées
 * séparément.</p>
 *
 * @author Fabrice Luneau
 */
public class EcritureNonEquilibreeException extends EcritureException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public EcritureNonEquilibreeException() {
        super("L'écriture n'est pas équilibrée : le total des débits doit être égal au total des crédits.");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public EcritureNonEquilibreeException(String message) {
        super(message);
    }
}
