package fr.xcompta.core.date.exception;

/**
 * @author Fabrice Luneau
 * 
 *  Une opération(écriture) ne peut pas être enregistrée :
 *  à ou avant la date de cloture.
 *  Elle peut être enregistrée dans le futur, sil apeeriode est ouverte
 *  Elle doit être enregistrée sur une période ouverte
 *  Sur l'exercisse comptable en cours
 *  ne peu pas être enrefistré sur un exercisse cloturé   
 */
	public class DateInvalideException extends DateComptableException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateInvalideException() {
		super("La date est invalide.");
	}

	public DateInvalideException(String message) {
		super(message);
	}
}
