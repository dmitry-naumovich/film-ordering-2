package by.epam.naumovich.film_ordering.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.dao.DAOFactory;
import by.epam.naumovich.film_ordering.dao.IReviewDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.AddReviewServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;

/**
 * IReviewService interface implementation that works with IReviewDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class ReviewServiceImpl implements IReviewService {

	private static final String MYSQL = "mysql";
	public static final String POSITIVE_REVIEW = "ps";
	public static final String NEGATIVE_REVIEW = "ng";
	public static final String NEUTRAL_REVIEW = "nt";
	public static final int REVIEW_MIN_LENGTH = 50;
	private static final int REVIEWS_AMOUNT_ON_PAGE = 5;
	
	@Override
	public void addReview(int userID, int filmID, String mark, String type, String text) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(filmID) || !Validator.validateStrings(mark, type, text)) {
			throw new AddReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		int rMark = Integer.parseInt(mark);
		if (rMark < 1 || rMark > 5) {
			throw new AddReviewServiceException(ExceptionMessages.REVIEW_MARK_RANGE);
		}
		if (!type.equals(POSITIVE_REVIEW) && !type.equals(NEGATIVE_REVIEW) && !type.equals(NEUTRAL_REVIEW)) {
			throw new AddReviewServiceException(ExceptionMessages.INVALID_REVIEW_TYPE);
		}
		if (text.length() < REVIEW_MIN_LENGTH) {
			throw new AddReviewServiceException(ExceptionMessages.REVIEW_TEXT_LENGTH);
		}
		
		Review review = new Review();
		review.setAuthor(userID);
		review.setFilmId(filmID);
		review.setMark(rMark);
		review.setType(type);
		review.setDate(Date.valueOf(LocalDate.now()));
		review.setTime(Time.valueOf(LocalTime.now()));
		review.setText(text);
		
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			reviewDAO.addReview(review);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
	}
	
	@Override
	public void deleteReview(int userID, int filmID) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(filmID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			reviewDAO.deleteReview(userID, filmID);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}

	}

	@Override
	public List<Review> getAllReviews() throws ServiceException {
		List<Review> set = new ArrayList<Review>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			set = reviewDAO.getAllReviews();
			
			if (set.isEmpty()) {
				throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return set;
	}

	@Override
	public List<Review> getReviewsByUserId(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		List<Review> set = new ArrayList<Review>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			set = reviewDAO.getReviewsByUserId(id);
			
			if (set.isEmpty()) {
				throw new GetReviewServiceException(ExceptionMessages.NO_USER_REVIEWS_YET);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return set;
	}

	@Override
	public List<Review> getReviewsByFilmId(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		List<Review> set = new ArrayList<Review>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			set = reviewDAO.getReviewsByFilmId(id);
			
			if (set.isEmpty()) {
				throw new GetReviewServiceException(ExceptionMessages.NO_FILM_REVIEWS);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return set;
	}

	@Override
	public Review getReviewByUserAndFilmId(int userID, int filmID) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(filmID)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			Review review = reviewDAO.getReviewByUserAndFilmId(userID, filmID);
			
			if (review == null) {
				throw new GetReviewServiceException(ExceptionMessages.NO_FILM_USER_REVIEW);
			}
			return review;
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public List<Review> getAllReviewsPart(int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		
		int start = (pageNum - 1) * REVIEWS_AMOUNT_ON_PAGE;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			List<Review> set = reviewDAO.getAllReviewsPart(start, REVIEWS_AMOUNT_ON_PAGE);
			
			if (set.isEmpty()) {
				throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
			}
			
			return set;
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public int getNumberOfAllReviewsPages() throws ServiceException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			int numOfReviews = reviewDAO.getNumberOfReviews();
			if (numOfReviews % REVIEWS_AMOUNT_ON_PAGE == 0) {
				return numOfReviews / REVIEWS_AMOUNT_ON_PAGE;
			}
			else {
				return numOfReviews / REVIEWS_AMOUNT_ON_PAGE + 1;
			}
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public List<Review> getReviewsPartByUserId(int userID, int pageNum) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(pageNum)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		List<Review> set = new ArrayList<Review>();
		int start = (pageNum - 1) * REVIEWS_AMOUNT_ON_PAGE;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			set = reviewDAO.getReviewsPartByUserId(userID, start, REVIEWS_AMOUNT_ON_PAGE);
			
			if (set.isEmpty()) {
				throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return set;
	}

	@Override
	public List<Review> getReviewsPartByFilmId(int filmID, int pageNum) throws ServiceException {
		if (!Validator.validateInt(filmID) || !Validator.validateInt(pageNum)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		List<Review> set = new ArrayList<Review>();
		int start = (pageNum - 1) * REVIEWS_AMOUNT_ON_PAGE;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			set = reviewDAO.getReviewsPartByFilmId(filmID, start, REVIEWS_AMOUNT_ON_PAGE);
			
			if (set.isEmpty()) {
				throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return set;
	}

	@Override
	public int getNumberOfUserReviewsPages(int userID) throws ServiceException {
		if (!Validator.validateInt(userID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			int numOfReviews = reviewDAO.getNumberOfUserReviews(userID);
			if (numOfReviews % REVIEWS_AMOUNT_ON_PAGE == 0) {
				return numOfReviews / REVIEWS_AMOUNT_ON_PAGE;
			}
			else {
				return numOfReviews / REVIEWS_AMOUNT_ON_PAGE + 1;
			}
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public int getNumberOfFilmReviewsPages(int filmID) throws ServiceException {
		if (!Validator.validateInt(filmID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IReviewDAO reviewDAO = daoFactory.getReviewDAO();
			int numOfReviews = reviewDAO.getNumberOfFilmReviews(filmID);
			if (numOfReviews % REVIEWS_AMOUNT_ON_PAGE == 0) {
				return numOfReviews / REVIEWS_AMOUNT_ON_PAGE;
			}
			else {
				return numOfReviews / REVIEWS_AMOUNT_ON_PAGE + 1;
			}
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

}
