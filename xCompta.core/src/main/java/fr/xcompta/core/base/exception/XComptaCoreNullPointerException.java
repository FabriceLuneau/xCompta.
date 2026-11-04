
package fr.xcompta.core.base.exception;

/**
 * Exception levée lorsqu'une référence obligatoire est {@code null}
 * dans une opération du noyau métier xCompta.
 *
 * <p>Cette exception permet de signaler une référence nulle sans
 * créer une classe d'exception spécifique pour chaque attribut
 * des objets métier.</p>
 *
 * <p>Le message doit préciser l'objet et l'attribut concernés.</p>
 *
 * <p>Cette exception est vérifiée (checked) puisqu'elle hérite
 * de {@link XComptaCoreException}.</p>
 *
 * @author Fabrice Luneau
 */
public class XComptaCoreNullPointerException extends XComptaCoreException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception sans message détaillé.
     */
    public XComptaCoreNullPointerException() {
        super();
    }

    /**
     * Construit une exception avec un message détaillé.
     *
     * @param message description de la référence nulle
     */
    public XComptaCoreNullPointerException(String message) {
        super(message);
    }
}
