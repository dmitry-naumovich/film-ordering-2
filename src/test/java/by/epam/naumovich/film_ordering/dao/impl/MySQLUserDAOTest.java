package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.dao.DAOFactory;
import by.epam.naumovich.film_ordering.dao.IUserDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;


/**
 * Tests DAO layer methods overridden in MySQLUserDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLUserDAOTest {

	/**
	 * Database type used in this test suite.
	 * 
	 */
	private static final String MYSQL = "mysql";
	
	/**
	 * This object will be compared to the actual object taken from the DAO layer.
	 * 
	 */
	private User expectedUser;
	
	/**
	 * This object will be compared to the actual object taken from the DAO layer.
	 * 
	 */
	private Discount expectedDiscount;
	
	/**
	 * Executes every time before a single method call and initializes expected User object.
	 * 
	 */
	@Before
	public void initTestReview() {
		expectedUser = new User();
		expectedUser.setLogin("testlogin");
		expectedUser.setName("testname");
		expectedUser.setSurname("testsurname");
		expectedUser.setSex('m');
		expectedUser.setType('c');
		expectedUser.setRegDate(Date.valueOf(LocalDate.now()));
		expectedUser.setRegTime(Time.valueOf(LocalTime.now()));
		expectedUser.setEmail("testemail@text.com");
		expectedUser.setPassword("testpass");
		expectedUser.setAbout("aboutuser test text");
	}
	
	/**
	 * Executes every time before a single method call and initializes expected Discount object.
	 * 
	 */
	@Before
	public void initTestDiscount() {
		expectedDiscount = new Discount();
		expectedDiscount.setUserID(2);
		expectedDiscount.setAmount(10);
		expectedDiscount.setStDate(Date.valueOf(LocalDate.now()));
		expectedDiscount.setStTime(Time.valueOf(LocalTime.now()));
		expectedDiscount.setEnDate(Date.valueOf("2020-01-01"));
		expectedDiscount.setEnTime(Time.valueOf(LocalTime.now()));
	}
	
	/**
	 * Adds expectedUser to the data source via DAO layer, gets it back and compares two results.
	 * Tests if the user was correctly added.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void addUser() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int id = dao.addUser(expectedUser);
		User actualUser = dao.getUserByID(id);
		dao.deleteUser(id);
		
		Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
		Assert.assertEquals(expectedUser.getName(), actualUser.getName());
		Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
		Assert.assertEquals(expectedUser.getSex(), actualUser.getSex());
		Assert.assertEquals(expectedUser.getType(), actualUser.getType());
		Assert.assertEquals(expectedUser.getRegDate(), actualUser.getRegDate());
		Assert.assertEquals(expectedUser.getRegTime(), actualUser.getRegTime());
		Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assert.assertEquals(expectedUser.getAbout(), actualUser.getAbout());
	}
	
	/**
	 * Adds expectedUser to the data source via DAO layer, updates it, gets it back and compares two results.
	 * Tests if the user was correctly edited.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void updateUser() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int id = dao.addUser(expectedUser);
		expectedUser.setName("upd text name");
		expectedUser.setPassword("new-password");
		expectedUser.setSex('f');
		dao.updateUser(id, expectedUser);
		User actualUser = dao.getUserByID(id);
		dao.deleteUser(id);
		
		Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
		Assert.assertEquals(expectedUser.getName(), actualUser.getName());
		Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
		Assert.assertEquals(expectedUser.getSex(), actualUser.getSex());
		Assert.assertEquals(expectedUser.getType(), actualUser.getType());
		Assert.assertEquals(expectedUser.getRegDate(), actualUser.getRegDate());
		Assert.assertEquals(expectedUser.getRegTime(), actualUser.getRegTime());
		Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assert.assertEquals(expectedUser.getAbout(), actualUser.getAbout());
	}
	
	/**
	 * Adds expectedUser to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the user was correctly deleted.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void deleteUser() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int id = dao.addUser(expectedUser);
		dao.deleteUser(id);
		User actualUser = dao.getUserByID(id);
		
		Assert.assertNull(actualUser);
	}
	
	/**
	 * Gets users in ban and separately iterates over all users checking every one of them if he is in ban, 
	 * removing them from the first collection if true. The expected result is empty collection.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getUsersInBan() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		Set<User> usersInBan = dao.getUsersInBan();
		
		for (User u : dao.getAllUsers()) {
			if (dao.userIsInBan(u.getId())) {
				usersInBan.remove(u);
			}
		}
		
		Assert.assertTrue(usersInBan.isEmpty());	
	}
	
	/**
	 * Adds expectedUser to the data source via DAO layer, gets it back by ID and compares two results.
	 * Tests if valid user is returned by id.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getUserByID() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int id = dao.addUser(expectedUser);
		User actualUser = dao.getUserByID(id);
		dao.deleteUser(id);
		
		Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
		Assert.assertEquals(expectedUser.getName(), actualUser.getName());
		Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
		Assert.assertEquals(expectedUser.getSex(), actualUser.getSex());
		Assert.assertEquals(expectedUser.getType(), actualUser.getType());
		Assert.assertEquals(expectedUser.getRegDate(), actualUser.getRegDate());
		Assert.assertEquals(expectedUser.getRegTime(), actualUser.getRegTime());
		Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assert.assertEquals(expectedUser.getAbout(), actualUser.getAbout());
	}
	
	/**
	 * Adds expectedUser to the data source via DAO layer, gets it back by login and compares two results.
	 * Tests if valid user is returned by login.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getUserByLogin() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int id = dao.addUser(expectedUser);
		User actualUser = dao.getUserByLogin(expectedUser.getLogin());
		dao.deleteUser(id);
		
		Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
		Assert.assertEquals(expectedUser.getName(), actualUser.getName());
		Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
		Assert.assertEquals(expectedUser.getSex(), actualUser.getSex());
		Assert.assertEquals(expectedUser.getType(), actualUser.getType());
		Assert.assertEquals(expectedUser.getRegDate(), actualUser.getRegDate());
		Assert.assertEquals(expectedUser.getRegTime(), actualUser.getRegTime());
		Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assert.assertEquals(expectedUser.getAbout(), actualUser.getAbout());	
	}
	
	/**
	 * Adds expectedUser to the data source via DAO layer, gets its password back by login and compares two results.
	 * Tests if valid user password is returned by login.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getPasswordByLogin() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int id = dao.addUser(expectedUser);
		String actualPassword = dao.getPasswordByLogin(expectedUser.getLogin());
		dao.deleteUser(id);
		
		Assert.assertEquals(expectedUser.getPassword(), actualPassword);
	}
	
	/**
	 * Adds expectedDiscount to the data source via DAO layer, gets it back by user ID and compares two results.
	 * Tests if valid discount entity is returned by user ID.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getCurrentUserDiscountByID() throws DAOException, InterruptedException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		dao.addDiscount(expectedDiscount);
		Thread.sleep(1000);
		Discount actualDiscount = dao.getCurrentUserDiscountByID(expectedDiscount.getUserID());
		dao.deleteDiscount(actualDiscount.getId());
		
		Assert.assertEquals(expectedDiscount.getAmount(), actualDiscount.getAmount());
		Assert.assertEquals(expectedDiscount.getUserID(), actualDiscount.getUserID());
		Assert.assertEquals(expectedDiscount.getStDate(), actualDiscount.getStDate());
		Assert.assertEquals(expectedDiscount.getStTime(), actualDiscount.getStTime());
		Assert.assertEquals(expectedDiscount.getEnDate(), actualDiscount.getEnDate());
		Assert.assertEquals(expectedDiscount.getEnTime(), actualDiscount.getEnTime());
		
	}
	
	/**
	 * Adds expectedDiscount to the data source via DAO layer, gets it back by user ID and compares two results.
	 * Tests if the discount was correctly added.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void addDiscount() throws DAOException, InterruptedException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int discountID = dao.addDiscount(expectedDiscount);
		Thread.sleep(1000);
		Discount actualDiscount = dao.getDiscountByID(discountID);
		dao.deleteDiscount(discountID);
		
		Assert.assertEquals(expectedDiscount.getAmount(), actualDiscount.getAmount());
		Assert.assertEquals(expectedDiscount.getUserID(), actualDiscount.getUserID());
		Assert.assertEquals(expectedDiscount.getStDate(), actualDiscount.getStDate());
		Assert.assertEquals(expectedDiscount.getStTime(), actualDiscount.getStTime());
		Assert.assertEquals(expectedDiscount.getEnDate(), actualDiscount.getEnDate());
		Assert.assertEquals(expectedDiscount.getEnTime(), actualDiscount.getEnTime());
	}
	
	/**
	 * Adds expectedDiscount to the data source via DAO layer, edits it, gets it back and compares two results.
	 * Tests if the discount was correctly edited.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void editDiscount() throws DAOException, InterruptedException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int discountID = dao.addDiscount(expectedDiscount);
		Thread.sleep(1000);
		expectedDiscount.setAmount(15);
		expectedDiscount.setStTime(expectedUser.getRegTime());
		expectedDiscount.setId(discountID);
		dao.editDiscount(expectedDiscount);
		Thread.sleep(1000);
		Discount actualDiscount = dao.getDiscountByID(discountID);
		dao.deleteDiscount(discountID);
		
		Assert.assertEquals(expectedDiscount.getAmount(), actualDiscount.getAmount());
		Assert.assertEquals(expectedDiscount.getUserID(), actualDiscount.getUserID());
		Assert.assertEquals(expectedDiscount.getStDate(), actualDiscount.getStDate());
		Assert.assertEquals(expectedDiscount.getStTime(), actualDiscount.getStTime());
		Assert.assertEquals(expectedDiscount.getEnDate(), actualDiscount.getEnDate());
		Assert.assertEquals(expectedDiscount.getEnTime(), actualDiscount.getEnTime());	
	}
	
	/**
	 * Adds expectedDiscount to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the discount was correctly deleted.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void deleteDiscount() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int discountID = dao.addDiscount(expectedDiscount);
		dao.deleteDiscount(discountID);
		Discount actualDiscount = dao.getDiscountByID(discountID);
		
		Assert.assertNull(actualDiscount);
	}
	
	/**
	 * Bans user, then returns boolean value if user is banned and unbans him.
	 * Tests if user was correctly banned.
	 * 
	 * @throws DAOException
	 * @throws InterruptedException 
	 */
	@Test
	public void userIsInBan() throws DAOException, InterruptedException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int testUserID = 1;
		dao.banUser(testUserID, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), 5, "Test ban reason");
		Thread.sleep(1000);
		boolean actual = dao.userIsInBan(testUserID);
		dao.unbanUser(testUserID);
		
		Assert.assertTrue(actual);	
	}
	
	/**
	 * Bans user, then returns boolean value if user is banned and unbans him.
	 * Tests if user was correctly banned.
	 * 
	 * @throws DAOException
	 * @throws InterruptedException 
	 */
	@Test
	public void banUser() throws DAOException, InterruptedException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int testUserID = 2;
		dao.banUser(testUserID, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), 5, "Test ban reason");
		Thread.sleep(1000);
		boolean actual = dao.userIsInBan(testUserID);
		dao.unbanUser(testUserID);
		
		Assert.assertTrue(actual);	
	}
	
	/**
	 * Bans user, then unbans him and expects false value checking.
	 * Tests if user was correctly unbanned.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void unbanUser() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int testUserID = 3;
		dao.banUser(testUserID, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), 5, "Test ban reason");
		dao.unbanUser(testUserID);
		boolean actual = dao.userIsInBan(testUserID);
		
		Assert.assertFalse(actual);	
	}
	
	/**
	 * Bans user, get ban reason and unbans him.
	 * Tests if correct ban reason value is returned.
	 * 
	 * @throws DAOException
	 * @throws InterruptedException 
	 */
	@Test
	public void getCurrentBanReason() throws DAOException, InterruptedException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO dao = daoFactory.getUserDAO();
		
		int testUserID = 5;
		String expectedBanReason = "Test ban reason";
		dao.banUser(testUserID, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), 5, expectedBanReason);
		Thread.sleep(1000);
		String actualBanReason = dao.getCurrentBanReason(testUserID);
		dao.unbanUser(testUserID);
		
		Assert.assertEquals(expectedBanReason, actualBanReason);	
	}
	
	/**
	 * Gets the amount of users in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfUsers() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO userDAO = daoFactory.getUserDAO();
		
		int usersNum1 = userDAO.getNumberOfUsers();
		Set<User> allUsers = userDAO.getAllUsers();
		int usersNum2 = allUsers.size();
		
		Assert.assertEquals(usersNum1, usersNum2);
	}
	
	/**
	 * Gets the part of all users from the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllUsersPart() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IUserDAO userDAO = daoFactory.getUserDAO();
		
		Set<User> particularUsers1 = userDAO.getAllUsersPart(0, 6);
		List<User> allUsers = new LinkedList<User>(userDAO.getAllUsers());
		Set<User> particularUsers2 = new LinkedHashSet<User>(allUsers.subList(0, 6));
		
		Assert.assertEquals(particularUsers1, particularUsers2);	
	}
}
