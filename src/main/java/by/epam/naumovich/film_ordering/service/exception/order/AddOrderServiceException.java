package by.epam.naumovich.film_ordering.service.exception.order;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that construct and transfer new orders to DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class AddOrderServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public AddOrderServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public AddOrderServiceException(String msg) {
		super(msg);
	}

}
