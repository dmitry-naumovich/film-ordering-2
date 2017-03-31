package by.epam.naumovich.film_ordering.service.exception.user;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that construct and transfer new bans to DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class BanUserServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public BanUserServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public BanUserServiceException(String msg) {
		super(msg);
	}

}
