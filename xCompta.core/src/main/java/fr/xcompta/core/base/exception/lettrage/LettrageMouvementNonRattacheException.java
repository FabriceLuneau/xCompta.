package fr.xcompta.core.base.exception.lettrage;

/**
 * Exception levée lorsqu'un mouvement attendu dans un lettrage n'est pas
 * réellement rattaché à ce lettrage.
 *
 * @author Fabrice Luneau
 */
public class LettrageMouvementNonRattacheException extends LettrageException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message par défaut.
     */
    public LettrageMouvementNonRattacheException() {
        super("Le mouvement n'appartient pas à ce lettrage.");
    }

    /**
     * Construit l'exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    public LettrageMouvementNonRattacheException(final String message) {
        super(message);
    }
}
