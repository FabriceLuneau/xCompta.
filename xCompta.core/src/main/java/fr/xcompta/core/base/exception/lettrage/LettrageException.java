package fr.xcompta.core.base.exception.lettrage;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Classe de base des exceptions relatives au lettrage comptable.
 *
 * <p>
 * Les exceptions dérivées signalent une violation d'une règle métier
 * empêchant la constitution, la modification ou la validation d'un lettrage.
 * </p>
 *
 * @author Fabrice Luneau
 */
public abstract class LettrageException extends XComptaCoreException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception sans message détaillé.
     */
    protected LettrageException() {
        super();
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message message décrivant l'erreur
     */
    protected LettrageException(final String message) {
        super(message);
    }
}
