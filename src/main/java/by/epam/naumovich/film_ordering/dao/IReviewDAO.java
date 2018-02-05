package by.epam.naumovich.film_ordering.dao;

import by.epam.naumovich.film_ordering.bean.ReviewPK;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.Review;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines methods for implementing in the DAO layer for the Review entity.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Transactional
public interface IReviewDAO extends CrudRepository<Review, ReviewPK> {

	/**
	 * Returns all reviews that are present in the data source
	 *
	 * @return a set of reviews
	 */
	List<Review> findAllByOrderByDateDescTimeDesc();

	/**
	 * Returns a necessary part of all reviews from the data source
	 * 
	 * @param start start index of necessary reviews part
	 * @param amount amount of reviews to be returned
	 * @return a part of the set of all reviews
	 */
	@Query(value = "SELECT * FROM reviews ORDER BY r_date DESC, r_time DESC LIMIT :start, :amount", nativeQuery = true)
	List<Review> findAllPart(@Param("start") int start, @Param("amount") int amount);
	
	/**
	 * Searches for reviews in the data source by user ID
	 * 
	 * @param author author ID
	 * @return a set of found reviews
	 */
	List<Review> findByIdAuthorOrderByDateDescTimeDesc(int author);
	
	/**
	 * Searches for the reviews in the data source by author ID and returns the necessary part of them
	 * 
	 * @param author author ID
	 * @param start start index of necessary part
	 * @param amount amount of reviews to be returned
	 * @return a set of found reviews
	 */
	@Query(value = "SELECT * FROM reviews WHERE r_author = :author ORDER BY r_date DESC, r_time DESC " +
            "LIMIT :start, :amount", nativeQuery = true)
	List<Review> getReviewsPartByUserId(@Param("author") int author, @Param("start") int start, @Param("amount") int amount);
	
	/**
	 * Searches for reviews in the data source by film ID
	 * 
	 * @param filmId film ID
	 * @return a set of found reviews
	 */
	@Query(value = "SELECT * FROM reviews WHERE r_film = :filmId ORDER BY r_date DESC, r_time DESC", nativeQuery = true)
	List<Review> getReviewsByFilmId(@Param("filmId") int filmId);
	
	/**
	 * Searches for the reviews in the data source by film ID
	 * 
	 * @param filmId film ID
	 * @param start start index of necessary part
	 * @param amount amount of reviews to be returned
	 * @return a set of found reviews
	 */
	@Query(value = "SELECT * FROM reviews WHERE r_film = :filmId ORDER BY r_date DESC, r_time DESC " +
            "LIMIT :start, :amount", nativeQuery = true)
	List<Review> getReviewsPartByFilmId(@Param("filmId") int filmId, @Param("start") int start, @Param("amount") int amount);

	/**
	 * Counts the number of user reviews in the data source
	 * 
	 * @param author ID of the author whose reviews are counted
	 * @return total user reviews amount
	 */
	@Query(value = "SELECT COUNT(*) FROM reviews WHERE r_author = :author", nativeQuery = true)
    long countByAuthor(@Param("author") int author);
	
	/**
	 * Counts the number of film reviews in the data source
	 * 
	 * @param filmId ID of the film which reviews are counted
	 * @return total film reviews amount
	 */
	@Query(value = "SELECT COUNT(*) FROM reviews WHERE r_film = :filmId", nativeQuery = true)
    long countByFilmId(@Param("filmId") int filmId);

    /**
     * Recounts and updates film rating
     *
     * @param filmId film id
     */
    @Modifying
	@Query(value = "UPDATE films SET f_rating = (SELECT AVG(r_mark) FROM reviews WHERE r_film = :filmId) " +
            "WHERE films.f_id = :filmId", nativeQuery = true)
	void updateFilmRating(@Param("filmId") int filmId);
}
