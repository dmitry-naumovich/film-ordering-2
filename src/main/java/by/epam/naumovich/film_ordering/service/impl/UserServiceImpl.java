package by.epam.naumovich.film_ordering.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.dao.IUserDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.BanUserServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetUserServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceAuthException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceSignUpException;
import by.epam.naumovich.film_ordering.service.exception.user.UserUpdateServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IUserService interface implementation that works with IUserDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

	private static final int MIN_YEAR = 1920;
	private static final int PHONE_NUM_LENGTH = 9;
	private static final char USER_TYPE_CLIENT = 'c';
	private static final int USERS_AMOUNT_ON_PAGE = 10;
	
	private static final String EMAIL_PATTERN = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[a-zA-Z]{2,4}$";
	private static final String LOGIN_PATTERN = "(^[a-zA-Z]{3,})[a-zA-Z0-9]*";
	private static final String PASSWORD_PATTERN = "^[a-zA-Z�-��-�0-9_-]{4,30}$";
	
	private final IUserDAO userDAO;

	@Autowired
    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
	public int addUser(String login, String name, String surname, String password, String sex, String bDate,
			String phone, String email, String about) throws ServiceException {

		try {
			User existingUser = userDAO.findByLogin(login);
			if (existingUser != null) {
				throw new ServiceSignUpException(ExceptionMessages.ALREADY_TAKEN_LOGIN);
			}
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		if (!Validator.validateWithPattern(login, LOGIN_PATTERN)) {
			throw new ServiceSignUpException(ExceptionMessages.INVALID_LOGIN);
		}
		if (!Validator.validateWithPattern(password, PASSWORD_PATTERN)) {
			throw new ServiceSignUpException(ExceptionMessages.INVALID_PASSWORD);
		}
		if (!Validator.validateWithPattern(email, EMAIL_PATTERN)) {
			throw new ServiceSignUpException(ExceptionMessages.INVALID_EMAIL);
		}
		if (!Validator.validateStrings(name, surname, sex)) {
			throw new ServiceSignUpException(ExceptionMessages.CORRUPTED_NAME_SURN_SEX);
		}
		
		User newUser = new User();
		newUser.setLogin(login);
		newUser.setName(name);
		newUser.setSurname(surname);
		newUser.setPassword(password);
		newUser.setSex(sex.charAt(0));
		newUser.setType(USER_TYPE_CLIENT);
		
		newUser.setRegDate(Date.valueOf(LocalDate.now()));
		newUser.setRegTime(Time.valueOf(LocalTime.now()));
		newUser = validateAndSetBirthdate(newUser, bDate);

		if (Validator.validateStrings(phone)) { 
			if (phone.length() != PHONE_NUM_LENGTH) {
				throw new UserUpdateServiceException(String.format(ExceptionMessages.INVALID_PHONE_NUM, PHONE_NUM_LENGTH));
			}
			newUser.setPhone(phone); 
		}
		newUser.setEmail(email);
		
		if (Validator.validateStrings(about)) { newUser.setAbout(about); }
		
		User createdUser = userDAO.save(newUser);
		if (createdUser == null) {
			throw new ServiceSignUpException(ExceptionMessages.UNSUCCESSFULL_SIGN_UP);
		}
		
		return createdUser.getId();
	}

	@Override
	public void updateUser(int id, String name, String surname, String password, String sex, String bDate, String phone,
			String email, String about) throws ServiceException {

		if (!Validator.validateInt(id)) {
			throw new UserUpdateServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		if (!Validator.validateWithPattern(password, PASSWORD_PATTERN)) {
			throw new UserUpdateServiceException(ExceptionMessages.INVALID_PASSWORD);
		}
		
		if (!Validator.validateWithPattern(email, EMAIL_PATTERN)) {
			throw new UserUpdateServiceException(ExceptionMessages.INVALID_EMAIL);
		}
		
		if (!Validator.validateStrings(name, surname, sex)) {
			throw new UserUpdateServiceException(ExceptionMessages.CORRUPTED_NAME_SURN_SEX);
		}

		User existingUser = userDAO.findOne(id);
		if (existingUser == null) {
		    throw new UserUpdateServiceException(ExceptionMessages.USER_NOT_FOUND);
        }
		
		User updUser = new User();
		updUser.setId(id);
		updUser.setLogin(existingUser.getLogin());
		updUser.setType(existingUser.getType());
		updUser.setRegDate(existingUser.getRegDate());
		updUser.setRegTime(existingUser.getRegTime());
		updUser.setName(name);
		updUser.setSurname(surname);
		updUser.setPassword(password);
		updUser.setSex(sex.charAt(0));
		updUser.setEmail(email);
        updUser = validateAndSetBirthdate(updUser, bDate);

		if (Validator.validateStrings(phone)) { 
			if (phone.length() != PHONE_NUM_LENGTH) {
				throw new UserUpdateServiceException(String.format(ExceptionMessages.INVALID_PHONE_NUM, PHONE_NUM_LENGTH));
			}
			updUser.setPhone(phone); 
		}
		if (Validator.validateStrings(about)) { updUser.setAbout(about); }
        
		userDAO.save(updUser);
	}
	
	@Override
	public void deleteUser(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		userDAO.delete(id);
	}
	
	@Override
	public User getUserByLogin(String login) throws ServiceException {
		if(!Validator.validateStrings(login)){
			throw new ServiceAuthException(ExceptionMessages.CORRUPTED_LOGIN);
		}
		
		try {
			User user = userDAO.findByLogin(login);
			if (user == null) {
				throw new ServiceAuthException(ExceptionMessages.LOGIN_NOT_REGISTRATED);
			}
			return user;
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public User getUserByID(int id) throws ServiceException {
		if(!Validator.validateInt(id)){
			throw new GetUserServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
        User user = userDAO.findOne(id);
        if (user == null) {
            throw new GetUserServiceException(ExceptionMessages.USER_NOT_FOUND);
        }
        return user;
	}

	@Override
	public User authenticate(String login, String password) throws ServiceException {
		if(!Validator.validateStrings(login, password)){
			throw new ServiceAuthException(ExceptionMessages.CORRUPTED_LOGIN_OR_PWD);
		}
		
		try {
			User user = userDAO.findByLogin(login);
			if (user == null) {
				throw new ServiceAuthException(ExceptionMessages.LOGIN_NOT_REGISTRATED);
			}
			
			String realPassw = userDAO.getPasswordByLogin(login);
			
			if (!realPassw.equals(password)) {
				throw new ServiceAuthException(ExceptionMessages.WRONG_PASSWORD);
			}
			
			if (userDAO.userIsInBan(user.getId())) {
				String untilDateAndTime = userDAO.getCurrentBanEnd(user.getId());
				String banReason = userDAO.getCurrentBanReason(user.getId());
				throw new ServiceAuthException(String.format(ExceptionMessages.YOU_ARE_BANNED, untilDateAndTime, banReason));
			}
			
			return user;
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);	
		}
	}

	@Override
	public String getLoginByID(int id) throws ServiceException {
		if(!Validator.validateInt(id)){
			throw new GetUserServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
        User user = userDAO.findOne(id);
        if (user == null) {
            throw new GetUserServiceException(ExceptionMessages.USER_NOT_FOUND);
        }
        return user.getLogin();
	}

	@Override
	public List<User> getAllUsers() throws ServiceException {
        List<User> users = userDAO.findAll();
        if (users == null) {
            throw new GetUserServiceException(ExceptionMessages.NO_USERS_IN_DB);
        }
        return users;
	}

	@Override
	public List<User> getUsersInBanNow() throws ServiceException {
		try {
			List<User> users = userDAO.findBanned();
			if (users == null) {
				throw new GetUserServiceException(ExceptionMessages.NO_USERS_IN_BAN_NOW);
			}
			return users;
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);	
		}	
	}

	@Override
	public void banUser(int userID, String length, String reason) throws ServiceException {
		if (!Validator.validateInt(userID)) {
			throw new BanUserServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		if (!Validator.validateStrings(length, reason)) {
			throw new BanUserServiceException(ExceptionMessages.CORRUPTED_LENGTH_OR_REASON);
		}
		int bLength;
		try {
			bLength = Integer.parseInt(length);
		} catch (NumberFormatException e) {
			throw new BanUserServiceException(ExceptionMessages.INVALID_BAN_LENGTH);
		}
		if (bLength < 0) {
			throw new BanUserServiceException(ExceptionMessages.INVALID_BAN_LENGTH);
		}
		
		Date startDate = Date.valueOf(LocalDate.now());
		Time startTime = Time.valueOf(LocalTime.now());
		
		try {
			userDAO.banUser(userID, startDate, startTime, bLength, reason);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
	}

	@Override
	public void unbanUser(int userID) throws ServiceException {
		if (!Validator.validateInt(userID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		try {
			userDAO.unbanUser(userID);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
	}

	@Override
	public boolean userIsInBan(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		try {
			return userDAO.userIsInBan(id);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public List<User> getAllUsersPart(int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetUserServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		int start = (pageNum - 1) * USERS_AMOUNT_ON_PAGE;
		
		try {
			List<User> users = userDAO.findAllPart(start, USERS_AMOUNT_ON_PAGE);
			if (users == null) {
				throw new GetUserServiceException(ExceptionMessages.NO_USERS_IN_DB);
			}
			return users;
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);	
		}	
	}

	@Override
	public int getNumberOfAllUsersPages() throws ServiceException {
        int numOfUsers = (int)userDAO.count(); //todo: return long everywhere
        if (numOfUsers % USERS_AMOUNT_ON_PAGE == 0) {
            return numOfUsers / USERS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfUsers / USERS_AMOUNT_ON_PAGE + 1;
        }
	}

    private User validateAndSetBirthdate(User user, String bDate) throws ServiceException {
        if (Validator.validateStrings(bDate)) {
            try {
                Date currentDate = Date.valueOf(LocalDate.now());
                Date birthDate = Date.valueOf(bDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(birthDate);

                if (birthDate.after(currentDate) || calendar.get(Calendar.YEAR) <= MIN_YEAR) {
                    throw new UserUpdateServiceException(String.format(ExceptionMessages.INVALID_BIRTHDATE, MIN_YEAR));
                }
                user.setBirthDate(Date.valueOf(bDate));
            } catch (IllegalArgumentException e) {
                throw new UserUpdateServiceException(ExceptionMessages.BIRTHDATE_RIGHT_FORMAT);
            }
        }
        return user;
    }
}
