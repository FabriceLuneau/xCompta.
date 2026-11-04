package fr.xcompta.core.base.exception.lettrage;

/**
 * Exception levée lorsqu'un lettrage contient moins de deux mouvements.
 *
 * @author Fabrice Luneau
 */
public class LettrageNbMouvementsInferieurADeuxException extends LettrageException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message par défaut.
     */
    public LettrageNbMouvementsInferieurADeuxException() {
        super("Un lettrage doit contenir au moins deux mouvements.");
    }

    /**
     * Construit l'exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    public LettrageNbMouvementsInferieurADeuxException(final String message) {
        super(message);
    }
}
