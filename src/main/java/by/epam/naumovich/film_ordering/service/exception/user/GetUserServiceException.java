package by.epam.naumovich.film_ordering.service.exception.user;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that get users from DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class GetUserServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetUserServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public GetUserServiceException(String msg) {
		super(msg);
	}

}
