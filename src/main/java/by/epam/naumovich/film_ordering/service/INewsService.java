package by.epam.naumovich.film_ordering.service;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Defines methods that receive parameters from Command implementations, verify them, construct necessary entities if needed 
 * and then pass them to the DAO layer, possibly getting some objects or primitive values back and passing them further back to the commands.
 * These methods operate with the News entity.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface INewsService {

	/**
	 * Constructs a new news entity based on input parameters received from the Controller layer, verifies them and either 
	 * passes to the DAO layer or throws an exception
	 * 
	 * @param title news title
	 * @param text news text
	 * @return ID of newly added news
	 * @throws ServiceException
	 */
	int create(String title, String text) throws ServiceException;
	
	/**
	 * Verifies the input parameter and either passes it to the DAO layer or throws an exception
	 * 
	 * @param id ID of news that will be deleted
	 * @throws ServiceException
	 */
	void delete(int id) throws ServiceException;
	
	/**
	 * Constructs edited news entity based on input parameters received from the Controller layer, verifies them
	 * and either passes to the DAO layer or throws an exception
	 * 
	 * @param id ID of news that will be edited
	 * @param title news title
	 * @param text news text
	 * @throws ServiceException
	 */
	void update(int id, String title, String text) throws ServiceException;
	
	/**
	 * Verifies the input parameter and passes it to the DAO layer, receives news entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param id news ID
	 * @return found news entity
	 * @throws ServiceException
	 */
	News getById(int id) throws ServiceException;

	/**
	 * Receives a set of four last news from the DAO layer and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @return a set of news
	 * @throws ServiceException
	 */
	List<News> getLastNews(int amount) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found news back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param year the year of the news that user is searching for
	 * @return a set of found news
	 * @throws ServiceException
	 */
	List<News> getByYear(int year) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found news back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 *
	 * @param month the month of the news that user is searching for
	 * @param year the year of the news that user is searching for
	 * @return a set of found news
	 * @throws ServiceException
	 */
	List<News> getByMonth(int month, int year) throws ServiceException;
	
	/**
	 * Receives a particular set of all news from the DAO layer depending on the current page
	 * and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @return a set of news
	 * @throws ServiceException
	 */
	List<News> getAllPart(int pageNum) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all news within the pagination.
	 * 
	 * @return number of pages
	 */
	long countPages();
}
