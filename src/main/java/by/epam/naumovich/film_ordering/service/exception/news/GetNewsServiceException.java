package by.epam.naumovich.film_ordering.service.exception.news;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that get news from DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class GetNewsServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetNewsServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public GetNewsServiceException(String msg) {
		super(msg);
	}

}
