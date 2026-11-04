package fr.xcompta.core.base.exception.lettrage;

/**
 * Exception levée lorsque la somme signée des mouvements d'un lettrage n'est
 * pas égale à zéro.
 *
 * @author Fabrice Luneau
 */
public class LettrageNonEquilibreException extends LettrageException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message par défaut.
     */
    public LettrageNonEquilibreException() {
        super("La somme des mouvements du lettrage doit être égale à zéro.");
    }

    /**
     * Construit l'exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    public LettrageNonEquilibreException(final String message) {
        super(message);
    }
}
