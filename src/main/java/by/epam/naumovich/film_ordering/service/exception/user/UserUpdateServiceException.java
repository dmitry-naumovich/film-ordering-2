package by.epam.naumovich.film_ordering.service.exception.user;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that update user profile settings
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class UserUpdateServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public UserUpdateServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public UserUpdateServiceException(String msg) {
		super(msg);
	}

}
