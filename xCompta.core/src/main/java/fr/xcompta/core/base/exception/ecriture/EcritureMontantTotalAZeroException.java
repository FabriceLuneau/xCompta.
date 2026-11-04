package fr.xcompta.core.base.exception.ecriture;

/**
 * Exception levée lorsque le montant total d'une écriture est nul.
 *
 * <p>Une écriture équilibrée doit avoir un total des débits égal au total
 * des crédits. Toutefois, ces deux totaux doivent être strictement
 * supérieurs à zéro.</p>
 *
 * <p>Une écriture dont le total des débits et le total des crédits sont
 * tous les deux égaux à zéro est donc équilibrée, mais n'est pas valide.</p>
 *
 * @author Fabrice Luneau
 */
public class EcritureMontantTotalAZeroException extends EcritureException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public EcritureMontantTotalAZeroException() {
        super("Le total des débits et des crédits d'une Ecriture doit être supérieur à zéro.");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public EcritureMontantTotalAZeroException(String message) {
        super(message);
    }
}
