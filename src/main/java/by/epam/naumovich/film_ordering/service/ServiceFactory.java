package by.epam.naumovich.film_ordering.service;

import by.epam.naumovich.film_ordering.service.impl.FilmServiceImpl;
import by.epam.naumovich.film_ordering.service.impl.NewsServiceImpl;
import by.epam.naumovich.film_ordering.service.impl.OrderServiceImpl;
import by.epam.naumovich.film_ordering.service.impl.ReviewServiceImpl;
import by.epam.naumovich.film_ordering.service.impl.UserServiceImpl;

/**
 * This is a service factory class which produces different service interface implementations each of which performs
 * a logic of working with project entities.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class ServiceFactory {
	
	/**
	 * Singleton ServiceFactory object which is the only one used by multiple Commands on Controller layer
	 */
	private static final ServiceFactory factory = new ServiceFactory();
	
	/**
	 * The access to singleton ServiceFactory object method
	 * @return ServiceFactory object
	 */
	public static ServiceFactory getInstance() {
		return factory;
	}
	
	/**
	 * @return IUserService implementation
	 */
	public IUserService getUserService() {
		return new UserServiceImpl();
	}
	
	/**
	 * @return IFilmService implementation
	 */
	public IFilmService getFilmService() {
		return new FilmServiceImpl();
	}
	
	/**
	 * @return IReviewService implementation
	 */
	public IReviewService getReviewService() {
		return new ReviewServiceImpl();
	}
	
	/**
	 * @return INewsService implementation
	 */
	public INewsService getNewsService() {
		return new NewsServiceImpl();
	}
	
	/**
	 * @return IOrderService implementation
	 */
	public IOrderService getOrderService() {
		return new OrderServiceImpl();
	}
}
