package by.epam.naumovich.film_ordering.service.exception.film;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that edit films
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class EditFilmServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public EditFilmServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public EditFilmServiceException(String msg) {
		super(msg);
	}

}
