package by.epam.naumovich.film_ordering.service.exception.review;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that get reviews from DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class GetReviewServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetReviewServiceException(String msg) {
		super(msg);
	}
	
	public GetReviewServiceException(String msg, Exception e) {
		super(msg, e);
	}

}
