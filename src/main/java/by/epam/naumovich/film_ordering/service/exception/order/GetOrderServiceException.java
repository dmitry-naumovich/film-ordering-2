package by.epam.naumovich.film_ordering.service.exception.order;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Describes exceptions that may occur in service layer methods that get orders from DAO layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class GetOrderServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetOrderServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public GetOrderServiceException(String msg) {
		super(msg);
	}

}
