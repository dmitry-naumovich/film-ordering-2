package by.epam.naumovich.film_ordering.service.exception.user;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that authenticate users
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class ServiceAuthException extends ServiceException {
	
	private static final long serialVersionUID = 1L;

	public ServiceAuthException(String message) {
		super(message);
	}

	public ServiceAuthException(String message, Exception e) {
		super(message, e);
	}
}
