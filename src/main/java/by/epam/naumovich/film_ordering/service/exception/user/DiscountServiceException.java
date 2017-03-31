package by.epam.naumovich.film_ordering.service.exception.user;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that construct and transfer new discounts to DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class DiscountServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public DiscountServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public DiscountServiceException(String msg) {
		super(msg);
	}

}
