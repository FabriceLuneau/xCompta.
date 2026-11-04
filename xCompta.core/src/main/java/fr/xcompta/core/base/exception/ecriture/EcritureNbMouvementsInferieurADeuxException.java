package fr.xcompta.core.base.exception.ecriture;

/**
 * Exception levée lorsqu'une écriture comporte moins de deux mouvements.
 *
 * <p>Une écriture doit obligatoirement comporter au moins deux mouvements
 * afin de permettre l'enregistrement d'un débit et d'un crédit.</p>
 *
 * @author Fabrice Luneau
 */
public class EcritureNbMouvementsInferieurADeuxException extends EcritureException {

    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception avec le message par défaut.
     */
    public EcritureNbMouvementsInferieurADeuxException() {
        super("Une Ecriture doit référencer au moins deux mouvements.");
    }

    /**
     * Construit une exception avec le message spécifié.
     *
     * @param message le message décrivant l'erreur
     */
    public EcritureNbMouvementsInferieurADeuxException(String message) {
        super(message);
    }
}
