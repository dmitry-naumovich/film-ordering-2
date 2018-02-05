package by.epam.naumovich.film_ordering.dao.impl;

import by.epam.naumovich.film_ordering.bean.ReviewPK;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.dao.IReviewDAO;
import org.junit.Assert;

/**
 * Tests DAO layer methods overridden in MySQLReviewDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLReviewDAOTest {

	private IReviewDAO dao;

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
	 */
	@Test
	public void addReview() {
		dao.save(expectedReview);
        ReviewPK reviewPK = new ReviewPK(expectedReview.getAuthor(), expectedReview.getFilmId());
        Review actualReview = dao.findOne(reviewPK);
		dao.delete(reviewPK);

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
	 */
	@Test
	public void deleteReview() {
		dao.save(expectedReview);
		dao.delete(expectedReview);
        ReviewPK reviewPK = new ReviewPK(expectedReview.getAuthor(), expectedReview.getFilmId());
        Review actualReview = dao.findOne(reviewPK);
		
		Assert.assertNull(actualReview);
	}
	
	/**
	 * Gets reviews by user ID in two different ways and compares results which must be equal.
	 */
	@Test
	public void getReviewsByUserId() {
		List<Review> userReviews1 = dao.findByIdAuthorOrderByDateDescTimeDesc(1);
		List<Review> userReviews2 = new ArrayList<>();
		for (Review r : dao.findAll()) {
			if (r.getAuthor() == 1) {
				userReviews2.add(r);
			}
		}
		
		Assert.assertEquals(userReviews1, userReviews2);
	}
	
	/**
	 * Gets reviews by film ID in two different ways and compares results which must be equal.
	 */
	@Test
	public void getReviewsByFilmId() {
		List<Review> filmReviews1 = dao.getReviewsByFilmId(1);
		List<Review> filmReviews2 = new ArrayList<>();
		for (Review r : dao.findAll()) {
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
	 */
	@Test
	public void getReviewByUserAndFilmId() {
		dao.save(expectedReview);
		ReviewPK reviewPK = new ReviewPK(expectedReview.getAuthor(), expectedReview.getFilmId());
		Review actualReview = dao.findOne(reviewPK);
		dao.delete(expectedReview.getId());
		
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
	 */
	@Test
	public void getNumberOfReviews() {
		int reviewsNum1 = (int)dao.count();
		int reviewsNum2 = dao.findAllByOrderByDateDescTimeDesc().size();
		
		Assert.assertEquals(reviewsNum1, reviewsNum2);
	}
	
	/**
	 * Gets the part of all reviews from the data source in two different ways and compares the results which must be equal.
	 */
	@Test
	public void getAllReviewsPart() {
		List<Review> particularReviews1 = dao.findAllPart(0, 6);
		List<Review> allReviews = new ArrayList<>(dao.findAllByOrderByDateDescTimeDesc());
		List<Review> particularReviews2 = new ArrayList<>(allReviews.subList(0, 6));
		
		Assert.assertEquals(particularReviews1, particularReviews2);	
	}
	
	/**
	 * Gets reviews part by user ID in two different ways and compares results which must be equal.
	 */
	@Test
	public void getReviewsPartByUserId() {
		List<Review> userReviews1 = dao.getReviewsPartByUserId(1, 0, 3);
		List<Review> userReviews2 = new ArrayList<>();
		for (Review o : dao.findAll()) {
			if (o.getAuthor() == 1) {
				userReviews2.add(o);
			}
		}
		List<Review> list = new ArrayList<>(userReviews2);
		userReviews2 = new ArrayList<>(list.subList(0, 3));
		
		Assert.assertEquals(userReviews1, userReviews2);	
	}
	
	/**
	 * Gets reviews part by film ID in two different ways and compares results which must be equal.
	 */
	@Test
	public void getReviewsPartByFilmId() {
		List<Review> filmReviews1 = dao.getReviewsPartByFilmId(1, 0, 3);
		List<Review> filmReviews2 = new ArrayList<>();
		for (Review o : dao.findAll()) {
			if (o.getFilmId() == 1) {
				filmReviews2.add(o);
			}
		}
		List<Review> list = new ArrayList<>(filmReviews2);
		filmReviews2 = new ArrayList<>(list.subList(0, 3));
		
		Assert.assertEquals(filmReviews1, filmReviews2);	
	}
	
	/**
	 * Gets the amount of user reviews in the data source in two different ways and compares the results which must be equal.
	 */
	@Test
	public void getNumberOfUserReviews() {
		int reviewsNum1 = dao.countByAuthor(4);
		List<Review> userReviews = new ArrayList<>();
		for (Review o : dao.findAll()) {
			if (o.getAuthor() == 4) {
				userReviews.add(o);
			}
		}
		int reviewsNum2 = userReviews.size();
		
		Assert.assertEquals(reviewsNum1, reviewsNum2);
	}
	
	/**
	 * Gets the amount of film reviews in the data source in two different ways and compares the results which must be equal.
	 */
	@Test
	public void getNumberOfFilmReviews() {
		int reviewsNum1 = dao.countByFilmId(1);
		List<Review> filmReviews = new ArrayList<>();
		for (Review o : dao.findAll()) {
			if (o.getFilmId() == 1) {
				filmReviews.add(o);
			}
		}
		int reviewsNum2 = filmReviews.size();
		
		Assert.assertEquals(reviewsNum1, reviewsNum2);
	}
}


