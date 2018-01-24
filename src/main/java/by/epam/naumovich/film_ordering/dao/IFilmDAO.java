package by.epam.naumovich.film_ordering.dao;

import java.util.List;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Defines methods for implementing in the DAO layer for the Film entity.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IFilmDAO{

	/**
	 * Adds a new film to the data source
	 * 
	 * @param film new film entity
	 * @return ID of a newly added film or 0 if it was not added
	 * @throws DAOException
	 */
	int create(Film film) throws DAOException;
	
	/**
	 * Deletes a film from the data source
	 * 
	 * @param id ID of a film which will be deleted
	 * @throws DAOException
	 */
	void delete(int id) throws DAOException;
	
	/**
	 * Edits a film in the data source
	 * 
	 * @param id ID of a film which will be edited
	 * @param editedFilm film entity with edited fields
	 * @throws DAOException
	 */
	void update(int id, Film editedFilm) throws DAOException;
	
	/**
	 * Searches for a film in the data source by its ID considering language
	 * 
	 * @param id ID of a film
	 * @return found film or null if it was not found
	 * @throws DAOException
	 */
	Film getById(int id, String lang) throws DAOException;

	/**
	 * Returns film name by its ID
	 * 
	 * @param id ID of a film
	 * @param lang language of the source data to be returned
	 * @return the name of the film or null if it was not found
	 * @throws DAOException
	 */
	String getFilmNameByID(int id, String lang) throws DAOException;
	
	/**
	 * Returns twelve last added to the data source films
	 * 
	 * @param lang language of the source data to be returned
	 * @return a set of films
	 * @throws DAOException
	 */
	List<Film> getTwelveLastAddedFilms(String lang) throws DAOException;
	
	/**
	 * Returns all films that a present in the data source
	 * 
	 * @param lang language of the source data to be returned
	 * @return a set of all films
	 * @throws DAOException
	 */
	List<Film> getAll(String lang) throws DAOException;
	
	/**
	 * Returns a necessary part of all films from the data source
	 * 
	 * @param start start index of necessary film part
	 * @param amount amount of films to be returned
	 * @param lang language of the source data to be returned
	 * @return a part of the set of all films
	 * @throws DAOException
	 */
	List<Film> getAllPart(int start, int amount, String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by name 
	 * 
	 * @param name film name
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	List<Film> getFilmsByName(String name, String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by year
	 * 
	 * @param year film year
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	List<Film> getFilmsByYear(int year, String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by genre
	 * 
	 * @param genre film genre
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	List<Film> getFilmsByGenre(String genre, String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by country
	 * 
	 * @param country film country
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	List<Film> getFilmsByCountry(String country, String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by year range
	 * 
	 * @param yearFrom left border of the range (including)
	 * @param yearTo right border of the range (including)
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	List<Film> getFilmsBetweenYears(int yearFrom, int yearTo, String lang) throws DAOException;
	
	/**
	 * Returns all film genres that are present in the data source
	 * 
	 * @return an array of available genres
	 * @param lang language of the source data to be returned
	 * @throws DAOException
	 */
	String[] getAvailableGenres(String lang) throws DAOException;
	
	/**
	 * Returns all film countries that are present in the data source
	 * 
	 * @return an array of available countries
	 * @param lang language of the source data to be returned
	 * @throws DAOException
	 */
	String[] getAvailableCountries(String lang) throws DAOException;
	
	/**
	 * Counts the number of all films in the data source
	 * 
	 * @return total film amount
	 * @throws DAOException
	 */
	int getNumberOfFilms() throws DAOException;
}
