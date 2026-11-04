package fr.xcompta.core.base.exception.mouvement;

/**
 * Exception levée lorsqu'une règle d'attribution de l'identifiant technique
 * d'un mouvement n'est pas respectée.
 *
 * <p>
 * Un mouvement nouvellement créé possède l'identifiant {@code 0}. La couche
 * de persistance peut lui attribuer une seule fois un identifiant strictement
 * positif. Une fois attribué, cet identifiant devient immuable.
 * </p>
 *
 * @author Fabrice Luneau
 */
public class MouvementIdViolationContrainteException extends MouvementException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit l'exception avec le message indiqué.
     *
     * @param message description de la violation
     */
    public MouvementIdViolationContrainteException(final String message) {
        super(message);
    }
}
