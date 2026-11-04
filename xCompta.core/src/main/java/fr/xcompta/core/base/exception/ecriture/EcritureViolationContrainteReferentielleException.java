package fr.xcompta.core.base.exception.ecriture;

/**
 * Exception levée lorsqu'on tente de modifier l'id(la clef primaire) d'une écriture 
 *
 * <p>
 * On ne peut pas modifier un id d'écriture de brouillard ou validée.
 * pour une écriture validée l'id est définitif
 * l'id estdonnée par la persitance
 * on peut supprimer une écriture d brouillard puis la recrée mais l'id sera attribué par la persistance
 * pou
 * 
 *
 * @author Fabrice Luneau
 */
public class EcritureViolationContrainteReferentielleException extends EcritureException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public EcritureViolationContrainteReferentielleException() {
        super("Violation de contrainte référentiel une écriture ne peut pas changer d'Id");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public EcritureViolationContrainteReferentielleException(String message) {
        super(message);
    }
}
