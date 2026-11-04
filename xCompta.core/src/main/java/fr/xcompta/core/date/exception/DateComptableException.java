package fr.xcompta.core.date.exception;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * @author Fabrice Luneau
 *
 * Exception de base pour toutes les exceptions relative aux dates 
 */
	public abstract class DateComptableException extends XComptaCoreException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DateComptableException() {
		super();
	}

	protected void name() {
		
	}
	
	protected DateComptableException(String message) {
		super(message);
	}
}
