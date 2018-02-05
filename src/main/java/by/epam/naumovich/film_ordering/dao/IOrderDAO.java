package by.epam.naumovich.film_ordering.dao;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines methods for implementing in the DAO layer for the Order entity.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Transactional
public interface IOrderDAO extends CrudRepository<Order, Integer> {

	/**
	 * Searches for the order in the data source by user and film IDs
	 * 
	 * @param userId user ID
	 * @param filmId film ID
	 * @return found order or null if it was not found
	 * @throws DAOException
	 */
	@Query(value = "SELECT * FROM orders WHERE o_user = :userId AND o_film = :filmId", nativeQuery = true)
	Order findByUserIdAndFilmId(@Param("userId") int userId, @Param("filmId") int filmId) throws DAOException;
	
	/**
	 * Searches for the orders in the data source by user ID
	 * 
	 * @param id user ID
	 * @return a set of found orders
	 * @throws DAOException
	 */
	List<Order> findByUserIdOrderByDateDescTimeDesc(int id) throws DAOException;

	/**
	 * Searches for the orders in the data source by user ID and returns the necessary part of them
	 * 
	 * @param userId user ID
	 * @param start start index of necessary part
	 * @param amount amount of orders to be returned
	 * @return a set of found orders
	 * @throws DAOException
	 */
	@Query(value = "SELECT * FROM orders WHERE o_user = :userId ORDER BY o_date DESC, o_time DESC " +
            "LIMIT :start, :amount", nativeQuery = true)
	List<Order> findPartByUserId(@Param("userId") int userId, @Param("start") int start, @Param("amount") int amount) throws DAOException;
	
	/**
	 * Searches for the orders in the data source by film ID
	 * 
	 * @param id film ID
	 * @return a set of found orders
	 * @throws DAOException
	 */
	List<Order> findByFilmIdOrderByDateDescTimeDesc(int id) throws DAOException;
	
	/**
	 * Searches for the orders in the data source by film ID
	 * 
	 * @param filmId film ID
	 * @param start start index of necessary part
	 * @param amount amount of orders to be returned
	 * @return a set of found orders
	 * @throws DAOException
	 */
	@Query(value = "SELECT * FROM orders WHERE o_film = :filmId ORDER BY o_date DESC, o_time DESC " +
            "LIMIT :start, :amount", nativeQuery = true)
	List<Order> findPartByFilmId(@Param("filmId") int filmId, @Param("start") int start, @Param("amount") int amount) throws DAOException;
	
	/**
	 * Returns all orders that are present in the data source
	 *
	 * @return a set of all orders
	 * @throws DAOException
	 */
	List<Order> findAllByOrderByDateDescTimeDesc() throws DAOException;
	
	/**
	 * Returns a necessary part of all orders from the data source
	 * 
	 * @param start start index of necessary orders part
	 * @param amount amount of orders to be returned
	 * @return a part of the set of all orders
	 * @throws DAOException
	 */
	@Query(value = "SELECT * FROM orders ORDER BY o_date DESC, o_time DESC LIMIT :start, :amount", nativeQuery = true)
	List<Order> findAllPart(@Param("start") int start, @Param("amount") int amount) throws DAOException;
	
	/**
	 * Counts the number of user orders in the data source
	 * 
	 * @param userID ID of the user whose orders are counted
	 * @return total user orders amount
	 * @throws DAOException
	 */
	long countByUserId(int userID) throws DAOException;
	
	/**
	 * Counts the number of film orders in the data source
	 * 
	 * @param filmID ID of the film which orders are counted
	 * @return total film orders amount
	 * @throws DAOException
	 */
	long countByFilmId(int filmID) throws DAOException;
}
