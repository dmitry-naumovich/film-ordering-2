package by.epam.naumovich.film_ordering.service.exception.news;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that construct and transfer new news to DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class AddNewsServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public AddNewsServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public AddNewsServiceException(String msg) {
		super(msg);
	}

}
