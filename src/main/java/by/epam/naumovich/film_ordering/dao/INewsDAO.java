package by.epam.naumovich.film_ordering.dao;

import java.util.Set;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;

/**
 * Defines methods for implementing in the DAO layer for the News entity.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface INewsDAO {

	/**
	 * Adds new news to the data source
	 * 
	 * @param news new news entity
	 * @return ID of the newly added news or 0 if it was not added
	 * @throws DAOException
	 */
	int addNews(News news) throws DAOException;
	
	/**
	 * Deletes news from the data source
	 * 
	 * @param id ID of the news that will be deleted
	 * @throws DAOException
	 */
	void deleteNews(int id) throws DAOException;
	
	/**
	 * Edits news in the data source
	 * 
	 * @param id ID of the news that will be edited
	 * @param editedNews news entity with edited fields
	 * @throws DAOException
	 */
	void editNews(int id, News editedNews) throws DAOException;
	
	/**
	 * Searches for the news in the data source by its ID
	 * 
	 * @param id ID of the news
	 * @return found news or null if it was not found
	 * @throws DAOException
	 */
	News getNewsById(int id) throws DAOException;
	
	/**
	 * Returns all news that a present in the data source
	 * 
	 * @return a set of all data source news
	 * @throws DAOException
	 */
	Set<News> getAllNews() throws DAOException;
	
	/**
	 * Searches for news in the data source by year
	 * 
	 * @param year news year
	 * @return a set of found news
	 * @throws DAOException
	 */
	Set<News> getNewsByYear(int year) throws DAOException;
	
	/**
	 * Searches for news in the data source by month and year
	 * 
	 * @param month news month
	 * @param year news year
	 * @return a set of found news
	 * @throws DAOException
	 */
	Set<News> getNewsByMonthAndYear(int month, int year) throws DAOException;
	
	/**
	 * Returns a necessary part of all news from the data source
	 * 
	 * @param start start index of necessary news part
	 * @param amount amount of news to be returned
	 * @return a part of the set of all news
	 * @throws DAOException
	 */
	Set<News> getAllNewsPart(int start, int amount) throws DAOException;
	
	/**
	 * Counts the number of all news in the data source
	 * 
	 * @return total news amount
	 * @throws DAOException
	 */
	int getNumberOfNews() throws DAOException;

}
