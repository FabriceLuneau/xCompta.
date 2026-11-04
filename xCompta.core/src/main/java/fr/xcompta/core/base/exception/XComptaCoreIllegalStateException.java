package fr.xcompta.core.base.exception;

/**
 * Exception vérifiée signalant qu'une opération est incompatible avec l'état
 * actuel d'un objet métier ou compromettrait sa cohérence.
 *
 * <p>Le message précise l'objet concerné, son identifiant lorsqu'il est
 * disponible, et la contrainte empêchant l'opération.</p>
 *
 * @author Fabrice Luneau
 */
public class XComptaCoreIllegalStateException extends XComptaCoreException {

    private static final long serialVersionUID = 1L;

    /** Construit une exception sans message détaillé. */
    public XComptaCoreIllegalStateException() {
        super();
    }

    /**
     * Construit une exception avec un message explicatif.
     *
     * @param message description de l'état incompatible avec l'opération
     */
    public XComptaCoreIllegalStateException(String message) {
        super(message);
    }
}
