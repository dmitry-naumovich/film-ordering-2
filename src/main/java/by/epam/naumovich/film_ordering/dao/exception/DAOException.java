package by.epam.naumovich.film_ordering.dao.exception;

/**
 * Describes an exception that may occur in the DAO layer
 * 
 * @author Dmitry
 * @version 1.0
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs DAOException object with the exception message
	 * 
	 * @param message occured exception message
	 */
	public DAOException(String message) {
		super(message);
	}
	
	/**
	 * Constructs DAOException object with the exception message and Exception object
	 * 
	 * @param message occured exception message
	 * @param e Exception object
	 */
	public DAOException(String message, Exception e) {
		super(message, e);
	}
}
