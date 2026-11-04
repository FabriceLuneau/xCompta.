package fr.xcompta.core.date.exception;

/**
 * @author Fabrice Luneau
 * 
 * Il n'est pas possible de modifier ou d'ajouter une opération(écriture) après la date de fermeture même sur un exevercissde ouvert, si il est fermé temporairement ou définitevement 
 */
	public class PerriodeFermeeException extends DateComptableException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerriodeFermeeException() {
		super();
	}

	public PerriodeFermeeException(String message) {
		super(message);
	}

}
