package by.epam.naumovich.film_ordering.service.exception.film;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that get films from DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class GetFilmServiceException extends ServiceException {
	
	private static final long serialVersionUID = 1L;

	public GetFilmServiceException(String msg) {
		super(msg);
	}
	
	public GetFilmServiceException(String msg, Exception e) {
		super(msg, e);
	}

}
