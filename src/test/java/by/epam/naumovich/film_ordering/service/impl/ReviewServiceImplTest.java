package by.epam.naumovich.film_ordering.service.impl;

import org.junit.Test;

import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.AddReviewServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;

/**
 * Tests service layer methods overridden in ReviewServiceImpl class in a way of passing invalid parameters into service methods
 * and expecting exceptions on the output with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class ReviewServiceImplTest {
	
	/**
	 * Tries to add new review with invalid filmID and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddReviewServiceException.class)
	public void addReviewWithInvalidUserID() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.addReview(0, 1, "1", "ng", "test review text");
	}
	
	/**
	 * Tries to add new review with invalid mark and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddReviewServiceException.class)
	public void addReviewWithInvalidMark() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.addReview(0, 1, "-1", "ng", "test review text");
	}
	
	/**
	 * Tries to add new review with invalid filmID and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddReviewServiceException.class)
	public void addReviewWithInvalidReviewText() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.addReview(0, 1, "1", "ng", null);
	}
	
	/**
	 * Tries to delete the review by invalid user and film ID values and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void deleteReview() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.deleteReview(0, -1);
	}
	
	/**
	 * Tries to get reviews by invalid user ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetReviewServiceException.class)
	public void getReviewsByUserId() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getReviewsByUserId(-1);
	}
	
	/**
	 * Tries to get reviews by invalid film ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetReviewServiceException.class)
	public void getReviewsByFilmId() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getReviewsByFilmId(-1);
	}
	
	/**
	 * Tries to get reviews by invalid film and user ID values and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetReviewServiceException.class)
	public void getReviewByUserAndFilmId()  throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getReviewByUserAndFilmId(0, -1);
	}
	
	/**
	 * Tries to get reviews from the DAO layer by the invalid page number.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetReviewServiceException.class)
	public void getAllReviewsPart() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getAllReviewsPart(0);
	}
	
	/**
	 * Tries to get review from the DAO layer by the invalid page number and user ID.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetReviewServiceException.class)
	public void getReviewsPartByUserId() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getReviewsPartByUserId(-1, 0);
	}
	
	/**
	 * Tries to get review from the DAO layer by the invalid page number and film ID.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetReviewServiceException.class)
	public void getReviewsPartByFilmId() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getReviewsPartByFilmId(-1, 0);
	}
	
	/**
	 * Tries to get user reviews amount from the DAO layer by the invalid user ID.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void getNumberOfUserReviewsPages() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getNumberOfUserReviewsPages(-1);
	}
	
	/**
	 * Tries to get film reviews amount from the DAO layer by the invalid film ID.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void getNumberOfFilmReviewsPages() throws ServiceException {
		IReviewService service = ServiceFactory.getInstance().getReviewService();
		service.getNumberOfFilmReviewsPages(0);
	}
	
}
