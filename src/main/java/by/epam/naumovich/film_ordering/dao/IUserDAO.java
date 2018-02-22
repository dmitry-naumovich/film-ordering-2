package by.epam.naumovich.film_ordering.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines methods for implementing in the DAO layer for the User, Ban and Discount entities.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Transactional
public interface IUserDAO extends CrudRepository<User, Integer> {

	/**
	 * Returns all users that are present in the data source
	 *
	 * @return a set of all users
	 */
	@Override
	@Query(value = "SELECT * FROM users ORDER BY u_regdate DESC, u_regtime DESC", nativeQuery = true)
	List<User> findAll();
	
	/**
	 * Returns a necessary part of all users from the data source
	 * 
	 * @param start start index of necessary users part
	 * @param amount amount of users to be returned
	 * @return a part of the set of all users
	 */
	@Query(value = "SELECT * FROM users ORDER BY u_regdate DESC, u_regtime DESC LIMIT :start, :amount", nativeQuery = true)
	List<User> findAllPart(@Param("start") int start, @Param("amount") int amount);
	
	/**
	 * Searches for users that are banned at the moment
	 * 
	 * @return a set of found users
	 */
	@Query(value = "SELECT users.* FROM users JOIN bans ON users.u_id = bans.b_user " +
            "WHERE b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) " +
            "OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) " +
            "OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY))) " +
            "ORDER BY bans.b_stdate DESC, bans.b_sttime DESC", nativeQuery = true)
	List<User> findBanned();

	/**
	 * Searches for the user in the data source by its login
	 * 
	 * @param login user login
	 * @return found user or null if it was not found
	 */
	User findByLogin(String login);
	
	/**
	 * Returns the password of the user by its login
	 * 
	 * @param login user login
	 * @return string password or null if it was not found
	 */
	@Query(value = "SELECT u_passw FROM users WHERE u_login = :login", nativeQuery = true)
	String getPasswordByLogin(@Param("login") String login);
	
	/**
	 * Check if the user is banned at the moment
	 * 
	 * @param id user ID
	 * @return true if user is banned at the moment, false - otherwise
	 */
	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END FROM bans WHERE bans.b_user = :id AND b_active = 1 " +
            "AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) " +
            "AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY)))",
            nativeQuery = true)
	boolean userIsInBan(@Param("id") int id);
	
	/**
	 * Bans user (adds ban entity to the data source)
	 * 
	 * @param userId ID of the user that will be banned
	 * @param startDate ban start date
	 * @param startTime ban start time
	 * @param length ban length (days)
	 * @param reason ban reason
	 */
	@Modifying
	@Query(value = "INSERT INTO bans (b_user, b_stdate, b_sttime, b_length, b_reason) " +
            "VALUES (:userId, :startDate, :startTime, :length, :reason)", nativeQuery = true)
	void banUser(@Param("userId") int userId, @Param("startDate") Date startDate, @Param("startTime") Time startTime,
                 @Param("length") int length, @Param("reason") String reason);
	
	/**
	 * Unbans user immediately
	 * 
	 * @param userId ID of the user that will be unbanned
	 */
    @Modifying
	@Query(value = "UPDATE bans SET b_active = 0 WHERE b_user = :userId", nativeQuery = true)
	void unbanUser(@Param("userId") int userId);
	
	/**
	 * Returns user ban end date and time
	 * 
	 * @param userId user ID
	 * @return ban and date and time concatenated in one String object
	 */
    @Query(value = "SELECT DATE_ADD(b_stdate, INTERVAL b_length DAY), b_sttime FROM bans " +
            "WHERE b_user = :userId  AND b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) " +
            "OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) " +
            "OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY)))", nativeQuery = true)
	String getCurrentBanEnd(@Param("userId") int userId);
	
	/**
	 * Returns ban reason for the user
	 * 
	 * @param userId user ID
	 * @return ban reason or null if it was not found
	 */
    @Query(value = "SELECT b_reason FROM bans WHERE b_user = :userId AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) " +
            "OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) " +
            "OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY))) " +
            "AND b_active = 1", nativeQuery = true)
	String getCurrentBanReason(@Param("userId") int userId);

}
