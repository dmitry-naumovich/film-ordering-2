package by.epam.naumovich.film_ordering.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;

/**
 * Defines methods for implementing in the DAO layer for the User, Ban and Discount entities.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IUserDAO {

	/**
	 * Adds new user to the data source
	 * 
	 * @param user new user entity
	 * @return id of the newly added user or 0 if it was not added
	 * @throws DAOException
	 */
	int addUser(User user) throws DAOException;
	
	/**
	 * Updates user in the data source
	 * 
	 * @param id user ID
	 * @param updatedUser user entity with edited fields
	 * @throws DAOException
	 */
	void updateUser(int id, User updatedUser) throws DAOException;
	
	/**
	 * Deletes user from the data source by ID
	 * 
	 * @param id user ID
	 * @throws DAOException
	 */
	void deleteUser(int id) throws DAOException;
	
	/**
	 * Returns all users that are present in the data source
	 * 
	 * @return a set of all users
	 * @throws DAOException
	 */
	Set<User> getAllUsers() throws DAOException;
	
	/**
	 * Returns a necessary part of all users from the data source
	 * 
	 * @param start start index of necessary users part
	 * @param amount amount of users to be returned
	 * @return a part of the set of all users
	 * @throws DAOException
	 */
	Set<User> getAllUsersPart(int start, int amount) throws DAOException;
	
	/**
	 * Searches for users that are banned at the moment
	 * 
	 * @return a set of found users
	 * @throws DAOException
	 */
	Set<User> getUsersInBan() throws DAOException;
	
	/**
	 * Searches for the user in the data source by its ID
	 * 
	 * @param id user ID
	 * @return found user or null if it was not found
	 * @throws DAOException
	 */
	User getUserByID(int id) throws DAOException;
	
	/**
	 * Searches for the user in the data source by its login
	 * 
	 * @param login user login
	 * @return found user or null if it was not found
	 * @throws DAOException
	 */
	User getUserByLogin(String login) throws DAOException;
	
	/**
	 * Returns the password of the user by its login
	 * 
	 * @param login user login
	 * @return string password or null if it was not found
	 * @throws DAOException
	 */
	String getPasswordByLogin(String login) throws DAOException;
	
	/**
	 * Searches for user current discount by its ID
	 * 
	 * @param id user ID
	 * @return found discount or null if it was not found
	 * @throws DAOException
	 */
	Discount getCurrentUserDiscountByID(int id) throws DAOException;
	
	/**
	 * Searches for the discount in the data source by its ID
	 * 
	 * @param discountID discount ID
	 * @return found discount entity
	 * @throws DAOException
	 */
	Discount getDiscountByID(int discountID) throws DAOException;
	/**
	 * Adds new user discount to the data source
	 * 
	 * @param discount new discount entity
	 * @throws DAOException
	 */
	int addDiscount(Discount discount) throws DAOException;
	
	/**
	 * Edits the discount in the data source
	 * 
	 * @param editedDisc discount entity with edited fields
	 * @throws DAOException
	 */
	void editDiscount(Discount editedDisc) throws DAOException;
	
	/**
	 * Deletes discount from the data source (finishes it)
	 * 
	 * @param discountID discount ID
	 * @throws DAOException
	 */
	void deleteDiscount(int discountID) throws DAOException;
	
	/**
	 * Check if the user is banned at the moment
	 * 
	 * @param id user ID
	 * @return true if user is banned at the moment, false - otherwise
	 * @throws DAOException
	 */
	boolean userIsInBan(int id) throws DAOException;
	
	/**
	 * Bans user (adds ban entity to the data source)
	 * 
	 * @param userID ID of the user that will be banned
	 * @param startDate ban start date
	 * @param startTime ban start time
	 * @param length ban length (days)
	 * @param reason ban reason
	 * @throws DAOException
	 */
	void banUser(int userID, Date startDate, Time startTime, int length, String reason) throws DAOException;
	
	/**
	 * Unbans user immediately
	 * 
	 * @param userID ID of the user that will be unbanned
	 * @throws DAOException
	 */
	void unbanUser(int userID) throws DAOException;
	
	/**
	 * Returns user ban end date and time
	 * 
	 * @param userID user ID
	 * @return ban and date and time concatenated in one String object
	 * @throws DAOException
	 */
	String getCurrentBanEnd(int userID) throws DAOException;
	
	/**
	 * Returns ban reason for the user
	 * 
	 * @param userID user ID
	 * @return ban reason or null if it was not found
	 * @throws DAOException
	 */
	String getCurrentBanReason(int userID) throws DAOException;
	
	/**
	 * Counts the number of all users in the data source
	 * 
	 * @return total user amount
	 * @throws DAOException
	 */
	int getNumberOfUsers() throws DAOException;
	
}
