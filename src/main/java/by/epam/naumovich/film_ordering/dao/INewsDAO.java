package by.epam.naumovich.film_ordering.dao;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines methods for implementing in the DAO layer for the News entity.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Transactional
public interface INewsDAO extends CrudRepository<News, Integer> {

	/**
	 * Returns all news that a present in the data source
	 * 
	 * @return a set of all data source news
	 */
	List<News> findAllByOrderByDateDescTimeDesc();
	
	/**
	 * Searches for news in the data source by year
	 * 
	 * @param year news year
	 * @return a set of found news
	 */
	@Query(value = "SELECT * FROM news WHERE YEAR(n_date) = :year ORDER BY n_date DESC, n_time DESC", nativeQuery = true)
	List<News> findByYear(@Param("year") int year);
	
	/**
	 * Searches for news in the data source by month and year
	 * 
	 * @param month news month
	 * @param year news year
	 * @return a set of found news
	 */
	@Query(value = "SELECT * FROM news WHERE MONTH(n_date) = :month AND YEAR(n_date) = :year " +
            "ORDER BY n_date DESC, n_time DESC", nativeQuery = true)
	List<News> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
	
	/**
	 * Returns a necessary part of all news from the data source
	 * 
	 * @param start start index of necessary news part
	 * @param amount amount of news to be returned
	 * @return a part of the set of all news
	 */
	@Query(value = "SELECT * FROM news ORDER BY n_date DESC, n_time DESC LIMIT :start, :amount", nativeQuery = true)
	List<News> findAllPart(@Param("start") int start, @Param("amount") int amount);
}
