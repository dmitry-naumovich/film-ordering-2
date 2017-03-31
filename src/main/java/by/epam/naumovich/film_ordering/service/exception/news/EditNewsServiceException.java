package by.epam.naumovich.film_ordering.service.exception.news;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that edit news
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class EditNewsServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public EditNewsServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public EditNewsServiceException(String msg) {
		super(msg);
	}

}
