package fr.xcompta.core.base.exception.lettrage;

/**
 * Exception levée lorsqu'un mouvement que l'on souhaite ajouter appartient
 * déjà à un lettrage.
 *
 * @author Fabrice Luneau
 */
public class LettrageMouvementDejaLettreException extends LettrageException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message par défaut.
     */
    public LettrageMouvementDejaLettreException() {
        super("Le mouvement appartient déjà à un lettrage.");
    }

    /**
     * Construit l'exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    public LettrageMouvementDejaLettreException(final String message) {
        super(message);
    }
}
