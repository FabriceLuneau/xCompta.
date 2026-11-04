package fr.xcompta.core.base.exception.ecriture;

/**
 * Exception levée lorsqu'une écriture utilise plusieurs fois le même compte.
 *
 * <p>Une écriture ne peut pas faire référence plusieurs fois au même compte.
 * Lorsque plusieurs mouvements concernent un même compte, ils doivent être
 * regroupés en un seul mouvement.</p>
 *
 * <p>Une écriture ne peut pas non plus utiliser simultanément le même compte
 * au débit et au crédit.</p>
 *
 * @author Fabrice Luneau
 */
public class EcritureCompenseException extends EcritureException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public EcritureCompenseException() {
        super("Erreur de compensation : le même compte est utilisé plusieurs fois.");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public EcritureCompenseException(String message) {
        super(message);
    }
}
