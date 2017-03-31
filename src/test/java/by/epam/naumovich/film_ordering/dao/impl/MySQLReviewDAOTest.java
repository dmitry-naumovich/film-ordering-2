package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.dao.DAOFactory;
import by.epam.naumovich.film_ordering.dao.IReviewDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import org.junit.Assert;

/**
 * Tests DAO layer methods overridden in MySQLReviewDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLReviewDAOTest {

	/**
	 * Database type used in this test suite.
	 * 
	 */
	private static final String MYSQL = "mysql";
	/**
	 * This object will be compared to the actual object taken from the DAO layer.
	 * 
	 */
	private Review expectedReview;
	
	/**
	 * Executes every time before a single method call and initializes expected Review object.
	 * 
	 */
	@Before
	public void initTestReview() {
		expectedReview = new Review();
		expectedReview.setAuthor(5);
		expectedReview.setFilmId(1);
		expectedReview.setDate(Date.valueOf(LocalDate.now()));
		expectedReview.setTime(Time.valueOf(LocalTime.now()));
		expectedReview.setMark(3);
		expectedReview.setType("nt");
		expectedReview.setText("test review text");
	}
	
	/**
	 * Adds expectedReview to the data source via DAO layer, gets it back and compares two results.
	 * Tests if the review was correctly added.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void addReview() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();
		
		dao.addReview(expectedReview);
		Review actualReview = dao.getReviewByUserAndFilmId(expectedReview.getAuthor(), expectedReview.getFilmId());
		dao.deleteReview(expectedReview.getAuthor(), expectedReview.getFilmId());
		
		Assert.assertEquals(expectedReview.getAuthor(), actualReview.getAuthor());
		Assert.assertEquals(expectedReview.getFilmId(), actualReview.getFilmId());
		Assert.assertEquals(expectedReview.getDate(), actualReview.getDate());
		Assert.assertEquals(expectedReview.getTime(), actualReview.getTime());
		Assert.assertEquals(expectedReview.getMark(), actualReview.getMark());
		Assert.assertEquals(expectedReview.getType(), actualReview.getType());
		Assert.assertEquals(expectedReview.getText(), actualReview.getText());
	}
	
	/**
	 * Adds expectedFilm to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the film was correctly deleted.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void deleteReview() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();
		
		dao.addReview(expectedReview);
		dao.deleteReview(expectedReview.getAuthor(), expectedReview.getFilmId());
		Review actualReview = dao.getReviewByUserAndFilmId(expectedReview.getAuthor(), expectedReview.getFilmId());
		
		Assert.assertNull(actualReview);
	}
	
	/**
	 * Gets reviews by user ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getReviewsByUserId() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();
		
		Set<Review> userReviews1 = dao.getReviewsByUserId(1);
		Set<Review> userReviews2 = new LinkedHashSet<Review>();
		for (Review r : dao.getAllReviews()) {
			if (r.getAuthor() == 1) {
				userReviews2.add(r);
			}
		}
		
		Assert.assertEquals(userReviews1, userReviews2);
	}
	
	/**
	 * Gets reviews by film ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getReviewsByFilmId() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();
		
		Set<Review> filmReviews1 = dao.getReviewsByFilmId(1);
		Set<Review> filmReviews2 = new LinkedHashSet<Review>();
		for (Review r : dao.getAllReviews()) {
			if (r.getFilmId() == 1) {
				filmReviews2.add(r);
			}
		}
		
		Assert.assertEquals(filmReviews1, filmReviews2);
	}
	
	/**
	 * Adds expectedReview to the data source via DAO layer, gets it back by user and film IDs and compares two results.
	 * Tests if the valid review entity is returned by user and film IDs.
	 * Then tests if null object is returned again by same IDs after it deletion.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getReviewByUserAndFilmId() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();

		dao.addReview(expectedReview);
		Review actualReview = dao.getReviewByUserAndFilmId(expectedReview.getAuthor(), expectedReview.getFilmId());
		dao.deleteReview(expectedReview.getAuthor(), expectedReview.getFilmId());
		
		Assert.assertEquals(expectedReview.getAuthor(), actualReview.getAuthor());
		Assert.assertEquals(expectedReview.getFilmId(), actualReview.getFilmId());
		Assert.assertEquals(expectedReview.getDate(), actualReview.getDate());
		Assert.assertEquals(expectedReview.getTime(), actualReview.getTime());
		Assert.assertEquals(expectedReview.getMark(), actualReview.getMark());
		Assert.assertEquals(expectedReview.getType(), actualReview.getType());
		Assert.assertEquals(expectedReview.getText(), actualReview.getText());
	}
	
	/**
	 * Gets the amount of reviews in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfReviews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO reviewDAO = daoFactory.getReviewDAO();
		
		int reviewsNum1 = reviewDAO.getNumberOfReviews();
		int reviewsNum2 = reviewDAO.getAllReviews().size();
		
		Assert.assertEquals(reviewsNum1, reviewsNum2);
	}
	
	/**
	 * Gets the part of all reviews from the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllReviewsPart() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO reviewDAO = daoFactory.getReviewDAO();
		
		Set<Review> particularReviews1 = reviewDAO.getAllReviewsPart(0, 6);
		List<Review> allReviews = new LinkedList<Review>(reviewDAO.getAllReviews());
		Set<Review> particularReviews2 = new LinkedHashSet<Review>(allReviews.subList(0, 6));
		
		Assert.assertEquals(particularReviews1, particularReviews2);	
	}
	
	/**
	 * Gets reviews part by user ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getReviewsPartByUserId() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();
		
		Set<Review> userReviews1 = dao.getReviewsPartByUserId(1, 0, 3);
		Set<Review> userReviews2 = new LinkedHashSet<Review>();
		for (Review o : dao.getAllReviews()) {
			if (o.getAuthor() == 1) {
				userReviews2.add(o);
			}
		}
		List<Review> list = new LinkedList<Review>(userReviews2);
		userReviews2 = new LinkedHashSet<Review>(list.subList(0, 3));
		
		Assert.assertEquals(userReviews1, userReviews2);	
	}
	
	/**
	 * Gets reviews part by film ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getReviewsPartByFilmId() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO dao = daoFactory.getReviewDAO();
		
		Set<Review> filmReviews1 = dao.getReviewsPartByFilmId(1, 0, 3);
		Set<Review> filmReviews2 = new LinkedHashSet<Review>();
		for (Review o : dao.getAllReviews()) {
			if (o.getFilmId() == 1) {
				filmReviews2.add(o);
			}
		}
		List<Review> list = new LinkedList<Review>(filmReviews2);
		filmReviews2 = new LinkedHashSet<Review>(list.subList(0, 3));
		
		Assert.assertEquals(filmReviews1, filmReviews2);	
	}
	
	/**
	 * Gets the amount of user reviews in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfUserReviews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO reviewDAO = daoFactory.getReviewDAO();
		
		int reviewsNum1 = reviewDAO.getNumberOfUserReviews(4);
		Set<Review> userReviews = new LinkedHashSet<Review>();
		for (Review o : reviewDAO.getAllReviews()) {
			if (o.getAuthor() == 4) {
				userReviews.add(o);
			}
		}
		int reviewsNum2 = userReviews.size();
		
		Assert.assertEquals(reviewsNum1, reviewsNum2);
	}
	
	/**
	 * Gets the amount of film reviews in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfFilmReviews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		IReviewDAO reviewDAO = daoFactory.getReviewDAO();
		
		int reviewsNum1 = reviewDAO.getNumberOfFilmReviews(1);
		Set<Review> filmReviews = new LinkedHashSet<Review>();
		for (Review o : reviewDAO.getAllReviews()) {
			if (o.getFilmId() == 1) {
				filmReviews.add(o);
			}
		}
		int reviewsNum2 = filmReviews.size();
		
		Assert.assertEquals(reviewsNum1, reviewsNum2);
	}
}


