package by.epam.naumovich.film_ordering.service.exception.film;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that construct and transfer new films to DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class AddFilmServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public AddFilmServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public AddFilmServiceException(String msg) {
		super(msg);
	}

}
