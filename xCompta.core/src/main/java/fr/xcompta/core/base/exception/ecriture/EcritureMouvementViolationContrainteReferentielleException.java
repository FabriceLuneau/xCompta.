package fr.xcompta.core.base.exception.ecriture;

/**
 * Exception levée lorsqu'un Mouvement contenu dans la collection de mouvement ne reférence pas l'écriture qui la contient
 *
 * <p>Les mouvements d'une écriture contenu dans sa collecion mouvements doivent tous  faire référence à cette dernière.
 *
 * @author Fabrice Luneau
 */
public class EcritureMouvementViolationContrainteReferentielleException extends EcritureException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public EcritureMouvementViolationContrainteReferentielleException() {
        super("Violation de contrainte référentiel un Mouvement fait référence à une autre objet Ecriture");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public EcritureMouvementViolationContrainteReferentielleException(String message) {
        super(message);
    }
}
