package by.epam.naumovich.film_ordering.service.impl;

import org.junit.Test;

import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.BanUserServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.DiscountServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetUserServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceAuthException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceSignUpException;
import by.epam.naumovich.film_ordering.service.exception.user.UserUpdateServiceException;

/**
 * Tests service layer methods overridden in UserServiceImpl class in a way of passing invalid parameters into service methods
 * and expecting exceptions on the output with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class UserServiceImplTest {

	/**
	 * Tries to add new user with invalid login value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceSignUpException.class)
	public void addUserWithInvalidLogin() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.addUser("00123", "Name", "Surname", "Test password", "m", null, null, "testemail@mail.test", null);
	}
	

	/**
	 * Tries to add new user with invalid date of birth value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceSignUpException.class)
	public void addUserWithInvalidBirthdate() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.addUser("testlogin1", "Name", "Surname", "Test password", "m", "2020-13-13", null, "testemail@mail.test", null);
	}
	

	/**
	 * Tries to add new user with invalid login value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceSignUpException.class)
	public void addUserWithInvalidEmail() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.addUser("testlogin2", "Name", "Surname", "Test password", "m", null, null, "invalid@mail", null);
	}
	
	/**
	 * Tries to delete user by the invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void deleteUser() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.deleteUser(0);
	}
	
	/**
	 * Tries to update user by the invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=UserUpdateServiceException.class)
	public void updateUserByInvalidID() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.updateUser(0, "testname", "testsurname", "testpwd", "u", null, null, "testemail@mail.test", null);
	}
	
	/**
	 * Tries to update user with the invalid surname and password values and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=UserUpdateServiceException.class)
	public void updateUserWithInvalidInput() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.updateUser(1, "testname", null, null, "u", null, null, "testemail@mail.test", null);
	}
	
	/**
	 * Tries to authenticate user by the invalid and senseless login value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceAuthException.class)
	public void authenticateBySenselessLogin() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.authenticate(";;l'l'123sdfs'd", "pwd");
	}
	
	/**
	 * Tries to authenticate user by the invalid login and password values and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceAuthException.class)
	public void authenticateByInvalidInput() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.authenticate("", null);
	}
	
	/**
	 * Tries to get user by the invalid login value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceAuthException.class)
	public void getUserByInvalidLogin() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getUserByLogin("l;k;ks;df;wlefkw;ea;daslda");
	}
	
	/**
	 * Tries to get user by the null login value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceAuthException.class)
	public void getUserByNullLogin() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getUserByLogin(null);
	}
	
	/**
	 * Tries to get user by the zero (invalid) ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetUserServiceException.class)
	public void getUserByZeroID() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getUserByID(0);
	}
	
	/**
	 * Tries to get user by the nonexistent ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetUserServiceException.class)
	public void getUserByNonexistentID() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getUserByID(999999999);
	}
	
	/**
	 * Tries to get user login by the invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetUserServiceException.class)
	public void getLoginByID() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getLoginByID(-2);
	}
	

	/**
	 * Tries to get current user discount by the zero ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void getCurrentUserDiscountByID() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getCurrentUserDiscountByID(0);
	}

	/**
	 * Tries to add user discount with the invalid amount value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=DiscountServiceException.class)
	public void addDiscountWithInvalidAmount() throws ServiceException { 
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.addDiscount(1, "105", "2100-01-01", "15:00:00");
	}
	

	/**
	 * Tries to add user discount with the invalid end date value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=DiscountServiceException.class)
	public void addDiscountWithInvalidEndDate() throws ServiceException { 
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.addDiscount(1, "10", "2000-01-01", "15:00:00");
	}
	
	/**
	 * Tries to add user discount with the invalid end time value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=DiscountServiceException.class)
	public void addDiscountWithInvalidEndTime() throws ServiceException { 
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.addDiscount(1, "10", "2100-01-01", null);
	}
	
	/**
	 * Tries to edit user discount by the invalid discount ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=DiscountServiceException.class)
	public void editDiscount() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.editDiscount(0, "10", "2100-01-01", "15:00:00");
	}
	
	/**
	 * Tries to delete the discount by the invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=DiscountServiceException.class)
	public void deleteDiscount() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.deleteDiscount(0);
	}
	
	/**
	 * Tries to check if the user is banned at the moment by the invalid user ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void userIsInBan() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.userIsInBan(0);
	}
	
	/**
	 * Tries to ban user with the invalid length value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=BanUserServiceException.class)
	public void banUserWithInvalidLength() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.banUser(1, "-10", "test ban reason");
	}
	
	/**
	 * Tries to ban user with the invalid reason value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=BanUserServiceException.class)
	public void banUserWithInvalidReason() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.banUser(1, "10", null);
	}
	

	/**
	 * Tries to unban user by the invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void unbanUser() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.unbanUser(0);
	}
	
	/**
	 * Tries to get users from the DAO layer by the invalid page number.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetUserServiceException.class)
	public void getAllUsersPart() throws ServiceException {
		IUserService service = ServiceFactory.getInstance().getUserService();
		service.getAllUsersPart(0);
	}
}
