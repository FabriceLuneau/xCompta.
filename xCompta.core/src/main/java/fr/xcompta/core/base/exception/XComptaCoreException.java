package fr.xcompta.core.base.exception;

/**
 * Classe abstraite de base des exceptions vérifiées du noyau métier xCompta.
 *
 * <p>Elle permet de déclarer ou d'intercepter de manière générique les
 * exceptions métier, sans pouvoir être instanciée directement.</p>
 *
 * <p>Les classes dérivées précisent la nature de l'erreur. Cette hiérarchie
 * reste vérifiée (checked), car elle hérite de {@link Exception}.</p>
 *
 * @author Fabrice Luneau
 */
public abstract class XComptaCoreException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception sans message détaillé.
     */
    protected XComptaCoreException() {
        super();
    }

    /**
     * Construit une exception avec un message détaillé.
     *
     * @param message description de l'erreur
     */
    protected XComptaCoreException(String message) {
        super(message);
    }
}
