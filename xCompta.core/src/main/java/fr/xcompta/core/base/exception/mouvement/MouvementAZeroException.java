package fr.xcompta.core.base.exception.mouvement;

/**
 * Exception levée lorsqu'un mouvement possède un montant nul.
 *
 * <p>Un mouvement comptable doit obligatoirement avoir un montant
 * différent de zéro pour pouvoir être enregistré dans une écriture.</p>
 *
 * @author Fabrice Luneau
 */
public class MouvementAZeroException extends MouvementException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public MouvementAZeroException() {
        super("Un Mouvement doit avoir un montant différent de zéro.");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public MouvementAZeroException(String message) {
        super(message);
    }
}
