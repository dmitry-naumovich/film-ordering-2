package by.epam.naumovich.film_ordering.service.exception.user;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that get discounts from DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class GetDiscountServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetDiscountServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public GetDiscountServiceException(String msg) {
		super(msg);
	}

}
