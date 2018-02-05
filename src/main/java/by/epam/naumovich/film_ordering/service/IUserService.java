package by.epam.naumovich.film_ordering.service;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Defines methods that receive parameters from Command implementations, verify them, construct necessary entities if needed 
 * and then pass them to the DAO layer, possibly getting some objects or primitive values back and passing them further back to the commands.
 * These methods operate with User, Ban and Discount entities.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IUserService {
	
	/**
	 * Constructs a new user entity based on input parameters received from the Controller layer, verifies them and either 
	 * passes to the DAO layer or throws an exception
	 * 
	 * @param login user login
	 * @param name user name
	 * @param surname user surname
	 * @param password user password
	 * @param sex user sex
	 * @param bDate user bDate
	 * @param phone user phone
	 * @param email user email
	 * @param about 'about me' user field
	 * @return ID of newly added user
	 * @throws ServiceException
	 */
	int create(String login, String name, String surname, String password,
			   String sex, String bDate, String phone, String email, String about) throws ServiceException;
	
	/**
	 * Verifies the input parameter and either passes it to the DAO layer or throws an exception
	 * 
	 * @param id ID of the user that will be deleted
	 * @throws ServiceException
	 */
	void delete(int id) throws ServiceException;
	
	/**
	 * Constructs an updated user entity based on input parameters received from the Controller layer, verifies them
	 * and either passes to the DAO layer or throws an exception
	 * 
	 * @param id user ID
	 * @param name user name
	 * @param surname user surname
	 * @param password user password
	 * @param sex user sex
	 * @param bDate user bDate
	 * @param phone user phone
	 * @param email user email
	 * @param about 'about me' user field
	 * @throws ServiceException
	 */
	void update(int id, String name, String surname, String password,
				String sex, String bDate, String phone, String email, String about) throws ServiceException;
	
	/**
	 * Verifies user login and password, passes them to the DAO layer and return User entity back to the Controller if it is found
	 * or throws an exception if password is wrong or user with such login does not exist
	 * 
	 * @param login user login
	 * @param password user password
	 * @return found user entity
	 * @throws ServiceException
	 */
	User authenticate(String login, String password) throws ServiceException;
	
	/**
	 * Verifies the input parameter and passes it to the DAO layer, receives the user entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param login user login
	 * @return found user entity
	 * @throws ServiceException
	 */
	User getByLogin(String login) throws ServiceException;
	
	/**
	 * Verifies the input parameter and passes it to the DAO layer, receives the user entity, returns it back to the Controller layer
	 * or throws an exception if it equals null
	 * 
	 * @param id user ID
	 * @return found user entity
	 * @throws ServiceException
	 */
	User getByID(int id) throws ServiceException;
	
	/**
	 * Verifies the input parameter and passes it to the DAO layer, receives the String user login back and passes it to the Controller
	 * or throws an exception if it equals null
	 * 
	 * @param id user ID
	 * @return String object : user login
	 * @throws ServiceException
	 */
	String getLoginByID(int id) throws ServiceException;
	
	/**
	 * Receives a particular set of all users from the DAO layer depending on the current page
	 * and passes it back to the Controller layer or throws an exception if it is empty
	 * 
	 * @return a set of users
	 * @throws ServiceException
	 */
	List<User> getAllUsersPart(int pageNum) throws ServiceException;

	/**
	 * Verifies the input parameter, passes it to the DAO layer, receive boolean value and passes it back to the Controller layer
	 * 
	 * @param id user ID
	 * @return true if user is in ban at the moment, false otherwise
	 * @throws ServiceException
	 */
	boolean isBanned(int id) throws ServiceException;
	
	/**
	 * Constructs a new ban entity based on input parameters received from the Controller layer, verifies them
	 * and either passes to the DAO layer or throws an exception
	 * 
	 * @param userId ID of the user who will be banned
	 * @param length ban length (days)
	 * @param reason ban reason
	 * @throws ServiceException
	 */
	void banUser(int userId, String length, String reason) throws ServiceException;
	
	/** 
	 * Verifies the input parameter and either passes it to the DAO layer or throws an exception
	 * 
	 * @param userId ID of the user who will be unbanned
	 * @throws ServiceException
	 */
	void unbanUser(int userId) throws ServiceException;
	
	/**
	 * Counts the number of pages needed to locate all users within the pagination.
	 * 
	 * @return number of pages
	 */
	long countPages();
}
