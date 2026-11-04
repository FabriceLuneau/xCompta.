package fr.xcompta.core.base.exception.lettrage;

/**
 * Exception levée lorsqu'un même mouvement apparaît plusieurs fois dans un
 * lettrage.
 *
 * @author Fabrice Luneau
 */
public class LettrageMouvementDupliqueException extends LettrageException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message par défaut.
     */
    public LettrageMouvementDupliqueException() {
        super("Un mouvement ne peut apparaître qu'une seule fois dans un lettrage.");
    }

    /**
     * Construit l'exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    public LettrageMouvementDupliqueException(final String message) {
        super(message);
    }
}
