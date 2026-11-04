package fr.xcompta.core.base.exception.ecriture;

				/**
*
*Exception levée lorsqu'une écriture tente d'intégrer un objet Mouvement à zéro dans sa collection de mouvements.
*les mouvements de la collection ne doivent pas être null
*les montants de la collection doivent être différent de 0 
*
*@author Fabrice Luneau
*/
public class EcritureMouvementAZeroException extends EcritureException {

private static final long serialVersionUID = 1L;

/**

Construit une exception avec le message par défaut.
*/
public EcritureMouvementAZeroException() {
super("Les mouvements de la collection mouvement d'une écriture ne peuvent pas être null");
}

/**

Construit une exception avec le message spécifié.


@param message le message décrivant l'erreur
*/
public EcritureMouvementAZeroException(String message) {
super(message);
}
}