package by.epam.naumovich.film_ordering.service;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Service layer interface for operating with Film entity.
 * Defines methods for obligatory implementation in concrete service classes.
 * The general contract for service layer concrete classes implies input parameters verification, constructing
 * new or updating existing entities if necessary, passing them to the data access layer.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IFilmService {

	/**
	 * Verifies input parameters, constructs a new film entity and passes it to the DAO layer
	 * 
	 * @param name film name
	 * @param year film year
	 * @param director film director
	 * @param cast film cast
	 * @param countries film countries
	 * @param composer film composer
	 * @param genres film genres
	 * @param length film length
	 * @param price film price
	 * @param description film description
	 * @return ID of created film
	 * @throws ServiceException - if any input parameters are not valid or the film was not created
	 */
	int create(String name, String year, String director, String cast, String[] countries, String composer,
               String[] genres, String length, String price, String description) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer
	 * 
	 * @param id ID of the film to be deleted
	 * @throws ServiceException - if input parameter is not valid
	 */
	void delete(int id) throws ServiceException;
	
	/**
	 * Verifies input parameters, fetches existing object from the DAO layer, updates it and passes to the DAO layer
	 * 
	 * @param id ID of the film to be edited
	 * @param name film name
	 * @param year film year
	 * @param director film director
	 * @param cast film cast
	 * @param countries film countries
	 * @param composer film composer
	 * @param genres film genres
	 * @param length film length
	 * @param price film price
	 * @param description film description
	 * @throws ServiceException - if any input parameters are not valid or the entity is not found in the DAO layer
	 */
	void update(int id, String name, String year, String director, String cast, String[] countries,
                String composer, String[] genres, String length, String price, String description) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, receives the film entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param id film ID
     * @param lang language
	 * @return found film entity
	 * @throws ServiceException - if input parameter is not valid or the entity is not found in the DAO layer
	 */
	Film getByID(int id, String lang) throws ServiceException;
	
	/**
	 * Verifies the input parameter and passes it to the DAO layer, receives the String name of the film back and passes it to the Controller
	 * or throws an exception if it equals null
	 * 
	 * @param id film ID
	 * @param lang language of the current user session
	 * @return String object : film name
	 * @throws ServiceException - if input parameter is not valid or the film name is not found in the DAO layer
	 */
	String getNameByID(int id, String lang) throws ServiceException;
	
	/**
	 * Receives a set of twelve last added films from the DAO layer and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @param lang language of the current user session
	 * @return a set of films
	 * @throws ServiceException - if no entities found in the DAO layer
	 */
	List<Film> getTwelveLastAddedFilms(String lang) throws ServiceException;

	/**
	 * Receives a particular set of all present films from the DAO layer depending on the page and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @param pageNum number of current page which user is opening
	 * @param lang language of the current user session
	 * @return a set of films
	 * @throws ServiceException - if any input parameters are not valid or no entities found within this page
	 */
	List<Film> getAllPart(int pageNum, String lang) throws ServiceException;
	
	/**
	 * Verifies input parameter and passes it to the DAO layer, received a set of found films back and returns it to the Controller layer
	 * or throws an exception if it is empty
	 * 
	 * @param text the name of the film that user is searching for
	 * @param lang language of the current user session
	 * @return a set of found films
	 * @throws ServiceException - if no entities found
	 */
	List<Film> searchByName(String text, String lang) throws ServiceException;
	
	/**
	 * Performs the logic of searching films by several criteria, verifies input parameters and requests necessary sets of films from the DAO layer,
	 * mixing them in the way that all acceptable by the criterion films would be returned back to the Controller layer
	 * 
	 * @param name film name
	 * @param yearFrom the left border of the year searching range
	 * @param yearTo the right border of the year searching range
	 * @param genres film genres array
	 * @param countries film countries array
	 * @param lang language of the current user session
	 * @return a set of found films or throws an exception if it is empty
	 * @throws ServiceException - if input parameters are not valid or no entities found
	 */
	List<Film> searchWidened(String name, String yearFrom, String yearTo, String[] genres, String[] countries, String lang) throws ServiceException;
	
	/**
	 * Receives the String array of all available genres from the DAO layer and passes them to the Controller layer
	 * 
	 * @param lang language of the current user session
	 * @return String array of available film genres
	 * @throws ServiceException - if no genres found
	 */
	String[] getAvailableGenres(String lang) throws ServiceException;

	/**
	 * Receives the String array of all available countries from the DAO layer and passes them to the Controller layer
	 * 
	 * @param lang language of the current user session
	 * @return String array of available film countries
	 * @throws ServiceException - if no countries found
	 */
	String[] getAvailableCountries(String lang) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all films within the pagination.
	 * 
	 * @return number of pages
	 */
	long countPages();
}
