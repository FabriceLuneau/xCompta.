
package fr.xcompta.core.base.exception;

/**
 * Exception levée lorsqu'un argument fourni à une opération
 * du noyau métier xCompta est invalide.
 *
 * <p>Cette exception permet de signaler une valeur incorrecte,
 * indépendamment de l'état actuel de l'objet métier, sans créer
 * une classe d'exception spécifique pour chaque attribut.</p>
 *
 * <p>Elle se distingue de {@link XComptaCoreNullPointerException},
 * qui signale une référence obligatoire {@code null}, et de
 * {@link XComptaCoreIllegalStateException}, qui signale une
 * opération incompatible avec l'état actuel d'un objet.</p>
 *
 * <p>Le message doit préciser l'objet et l'argument concernés,
 * ainsi que la raison pour laquelle la valeur est invalide.
 * L'identifiant de l'objet peut être indiqué lorsqu'il est
 * disponible.</p>
 *
 * <p>Cette exception est vérifiée (checked), puisqu'elle hérite
 * de {@link XComptaCoreException}.</p>
 *
 * @author Fabrice Luneau
 */
public class XComptaCoreIllegalArgumentException
        extends XComptaCoreException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception sans message détaillé.
     */
    public XComptaCoreIllegalArgumentException() {
        super();
    }

    /**
     * Construit une exception avec un message détaillé.
     *
     * @param message description de l'argument invalide
     */
    public XComptaCoreIllegalArgumentException(String message) {
        super(message);
    }
}
