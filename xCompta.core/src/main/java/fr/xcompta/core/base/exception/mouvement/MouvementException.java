package fr.xcompta.core.base.exception.mouvement;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Classe de base des exceptions relatives à un mouvement comptable.
 * <p>
 * Les exceptions dérivées permettent de signaler notamment qu'un mouvement
 * est invalide ou qu'il ne respecte pas les règles métier nécessaires à son
 * intégration dans une écriture comptable.
 * </p>
 *
 * @author Fabrice Luneau
 */
public abstract class MouvementException extends XComptaCoreException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception sans message détaillé.
     */
    protected MouvementException() {
        super();
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message message décrivant la cause de l'exception
     */
    protected MouvementException(String message) {
        super(message);
    }
}
