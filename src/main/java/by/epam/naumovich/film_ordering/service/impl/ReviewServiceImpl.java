package by.epam.naumovich.film_ordering.service.impl;

import by.epam.naumovich.film_ordering.bean.ReviewPK;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.dao.IReviewDAO;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.AddReviewServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IReviewService interface implementation that works with IReviewDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Service
public class ReviewServiceImpl implements IReviewService {

	private final IReviewDAO reviewDAO;

	@Autowired
	public ReviewServiceImpl(IReviewDAO reviewDAO) {
		this.reviewDAO = reviewDAO;
	}

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

        reviewDAO.save(review);
        reviewDAO.updateFilmRating(filmID);
	}
	
	@Override
	public void deleteReview(int userID, int filmID) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(filmID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}

        ReviewPK reviewPK = new ReviewPK(userID, filmID);
        reviewDAO.delete(reviewPK);
        reviewDAO.updateFilmRating(filmID);
	}

	@Override
	public List<Review> getAllReviews() throws ServiceException {
		List<Review> reviews = reviewDAO.findAllByOrderByDateDescTimeDesc();
			
        if (reviews.isEmpty()) {
            throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
        }
		
		return reviews;
	}

	@Override
	public List<Review> getReviewsByUserId(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		List<Review> set = reviewDAO.findByUserId(id);
			
        if (set.isEmpty()) {
            throw new GetReviewServiceException(ExceptionMessages.NO_USER_REVIEWS_YET);
        }
		
		return set;
	}

	@Override
	public List<Review> getReviewsByFilmId(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		List<Review> reviews = reviewDAO.getReviewsByFilmId(id);
			
        if (reviews.isEmpty()) {
            throw new GetReviewServiceException(ExceptionMessages.NO_FILM_REVIEWS);
        }
		
		return reviews;
	}

	@Override
	public Review getReviewByUserAndFilmId(int userID, int filmID) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(filmID)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
        Review review = reviewDAO.findOne(new ReviewPK(userID, filmID));

        if (review == null) {
            throw new GetReviewServiceException(ExceptionMessages.NO_FILM_USER_REVIEW);
        }
        return review;

	}

	@Override
	public List<Review> getAllReviewsPart(int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		
		int start = (pageNum - 1) * REVIEWS_AMOUNT_ON_PAGE;
        List<Review> list = reviewDAO.findAllPart(start, REVIEWS_AMOUNT_ON_PAGE);

        if (list.isEmpty()) {
            throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
        }

        return list;
	}

	@Override
	public int getNumberOfAllReviewsPages() throws ServiceException {
        int numOfReviews = (int)reviewDAO.count(); //todo: return long everywhere
        if (numOfReviews % REVIEWS_AMOUNT_ON_PAGE == 0) {
            return numOfReviews / REVIEWS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfReviews / REVIEWS_AMOUNT_ON_PAGE + 1;
        }
	}

	@Override
	public List<Review> getReviewsPartByUserId(int userID, int pageNum) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(pageNum)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
        int start = (pageNum - 1) * REVIEWS_AMOUNT_ON_PAGE;
        List<Review> list = reviewDAO.getReviewsPartByUserId(userID, start, REVIEWS_AMOUNT_ON_PAGE);

        if (list.isEmpty()) {
            throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
        }
		
		return list;
	}

	@Override
	public List<Review> getReviewsPartByFilmId(int filmID, int pageNum) throws ServiceException {
		if (!Validator.validateInt(filmID) || !Validator.validateInt(pageNum)) {
			throw new GetReviewServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}

        int start = (pageNum - 1) * REVIEWS_AMOUNT_ON_PAGE;
        List<Review> set = reviewDAO.getReviewsPartByFilmId(filmID, start, REVIEWS_AMOUNT_ON_PAGE);
			
        if (set.isEmpty()) {
            throw new GetReviewServiceException(ExceptionMessages.NO_REVIEWS_IN_DB);
        }
		return set;
	}

	@Override
	public int getNumberOfUserReviewsPages(int userID) throws ServiceException {
		if (!Validator.validateInt(userID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
        int numOfReviews = reviewDAO.countByAuthor(userID);
        if (numOfReviews % REVIEWS_AMOUNT_ON_PAGE == 0) {
            return numOfReviews / REVIEWS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfReviews / REVIEWS_AMOUNT_ON_PAGE + 1;
        }
	}

	@Override
	public int getNumberOfFilmReviewsPages(int filmID) throws ServiceException {
		if (!Validator.validateInt(filmID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
        int numOfReviews = reviewDAO.countByFilmId(filmID);
        if (numOfReviews % REVIEWS_AMOUNT_ON_PAGE == 0) {
            return numOfReviews / REVIEWS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfReviews / REVIEWS_AMOUNT_ON_PAGE + 1;
        }
	}

}
