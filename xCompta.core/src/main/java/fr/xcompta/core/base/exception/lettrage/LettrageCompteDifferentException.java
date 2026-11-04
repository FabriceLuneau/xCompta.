package fr.xcompta.core.base.exception.lettrage;

/**
 * Exception levée lorsque les mouvements d'un lettrage n'appartiennent pas
 * tous au même compte comptable.
 *
 * @author Fabrice Luneau
 */
public class LettrageCompteDifferentException extends LettrageException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message par défaut.
     */
    public LettrageCompteDifferentException() {
        super("Tous les mouvements d'un lettrage doivent appartenir au même compte.");
    }

    /**
     * Construit l'exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    public LettrageCompteDifferentException(final String message) {
        super(message);
    }
}
