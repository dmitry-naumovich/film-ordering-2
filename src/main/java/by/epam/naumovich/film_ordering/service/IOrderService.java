package by.epam.naumovich.film_ordering.service;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Defines methods that receive parameters from Command implementations, verify them, construct necessary entities if needed 
 * and then pass them to the DAO layer, possibly getting some objects or primitive values back and passing them further back to the commands.
 * These methods operate with the Order entity.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IOrderService {

	/**
	 * Constructs a new order entity based on input parameters received from the Controller layer, verifies them and either 
	 * passes to the DAO layer or throws an exception
	 * 
	 * @param filmID ordered film ID
	 * @param userID ordering user ID
	 * @param price film price
	 * @param discount user discount
	 * @param payment order total payment
	 * @return order number of newly added order
	 * @throws ServiceException
	 */
	int create(int filmID, int userID, String price, String discount, String payment) throws ServiceException;
	
	/**
	 * Verifies the input parameter and either passes it to the DAO layer or throws an exception
	 * 
	 * @param orderNum order number of the order to be deleted
	 * @throws ServiceException
	 */
	void delete(int orderNum) throws ServiceException;
	
	/**
	 * Verifies the input parameter and passes it to the DAO layer, receives the order entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param orderNum order number
	 * @return found order entity
	 * @throws ServiceException
	 */
	Order getByOrderNum(int orderNum) throws ServiceException;
	
	/**
	 * Verifies the input parameters and passes them to the DAO layer, receives the order entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param userID ID of the user whose order is searched
	 * @param filmID ID of the film which order is searched
	 * @return found order entity
	 * @throws ServiceException
	 */
	Order getByUserAndFilmId(int userID, int filmID) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found orders back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param id ID of the user whose orders are searched
	 * @return a set of found orders
	 * @throws ServiceException
	 */
	List<Order> getAllByUserId(int id) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a particular set of found orders back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param id ID of the user whose orders are searched
	 * @param pageNum number of page
	 * @return a set of found orders
	 * @throws ServiceException
	 */
	List<Order> getAllPartByUserId(int id, int pageNum) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found orders back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param id ID of the film which orders are searched
	 * @return a set of found orders
	 * @throws ServiceException
	 */
	List<Order> getAllByFilmId(int id) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a particular set of found orders back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param id ID of the film which orders are searched
	 * @param pageNum number of page
	 * @return a set of found orders
	 * @throws ServiceException
	 */
	List<Order> getAllPartByFilmId(int id, int pageNum) throws ServiceException;

	/**
	 * Receives a particular set of all orders from the DAO layer depending on the current page
	 * and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @return a set of orders
	 * @throws ServiceException
	 */
	List<Order> getAllPart(int pageNum) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all orders within the pagination.
	 * 
	 * @return number of pages
	 */
	int countPages();
	
	/**
	 * Counts the number of pages needed to locate all user orders within the pagination.
	 * 
	 * @param userId ID of the user whose orders are searched
	 * @return number of pages
	 * @throws ServiceException - if userID is not valid
	 */
	int countPagesByUserId(int userId) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all film orders within the pagination.
	 * 
	 * @param filmId ID of the film which orders are searched
	 * @return number of pages
	 * @throws ServiceException - if filmId is not valid
	 */
	int countPagesByFilmId(int filmId) throws ServiceException;
}