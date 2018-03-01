package by.epam.naumovich.film_ordering.dao;

import by.epam.naumovich.film_ordering.bean.News;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines methods for implementing in the DAO layer for the News entity.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Transactional
public interface INewsDAO extends PagingAndSortingRepository<News, Integer> {

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
}
