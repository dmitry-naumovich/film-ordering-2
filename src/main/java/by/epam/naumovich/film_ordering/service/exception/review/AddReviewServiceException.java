package by.epam.naumovich.film_ordering.service.exception.review;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that construct and transfer new reviews to DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class AddReviewServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public AddReviewServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public AddReviewServiceException(String msg) {
		super(msg);
	}

}
