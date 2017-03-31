package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.dao.DAOFactory;
import by.epam.naumovich.film_ordering.dao.INewsDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;

/**
 * Tests DAO layer methods overridden in MySQLNewsDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLNewsDAOTest {

	/**
	 * Database type used in this test suite.
	 * 
	 */
	private static final String MYSQL = "mysql";
	/**
	 * This object will be compared to the actual object taken from the DAO layer.
	 * 
	 */
	private News expectedNews;
	
	/**
	 * Executes every time before a single method call and initializes expected News object.
	 * 
	 */
	@Before
	public void initTestNews() {
		expectedNews = new News();
		expectedNews.setDate(Date.valueOf(LocalDate.now()));
		expectedNews.setTime(Time.valueOf(LocalTime.now()));
		expectedNews.setTitle("test news title");
		expectedNews.setText("test news text here");
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, gets it back and compares two results.
	 * Tests if the news was correctly added.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void addNews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		int id = dao.addNews(expectedNews);
		News actualNews = dao.getNewsById(id);
		dao.deleteNews(id);
		
		Assert.assertEquals(expectedNews.getDate(), actualNews.getDate());
		Assert.assertEquals(expectedNews.getTime(), actualNews.getTime());
		Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());	
		Assert.assertEquals(expectedNews.getText(), actualNews.getText());
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the news was correctly deleted.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void deleteNews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		int id = dao.addNews(expectedNews);
		dao.deleteNews(id);
		News actualNews = dao.getNewsById(id);
		
		Assert.assertNull(actualNews);
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, edits it, gets it back and compares two results.
	 * Tests if the news was correctly edited.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void editNews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		int id = dao.addNews(expectedNews);
		expectedNews.setTitle("updated title");
		expectedNews.setText("updated news text");
		dao.editNews(id, expectedNews);
		News actualNews = dao.getNewsById(id);
		dao.deleteNews(id);
		
		Assert.assertEquals(expectedNews.getDate(), actualNews.getDate());
		Assert.assertEquals(expectedNews.getTime(), actualNews.getTime());
		Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());	
		Assert.assertEquals(expectedNews.getText(), actualNews.getText());
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, gets it back by ID and compares two results.
	 * Tests if valid news entity is returned by id.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNewsById() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		int id = dao.addNews(expectedNews);
		News actualNews = dao.getNewsById(id);
		dao.deleteNews(id);
		
		Assert.assertEquals(expectedNews.getDate(), actualNews.getDate());
		Assert.assertEquals(expectedNews.getTime(), actualNews.getTime());
		Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());	
		Assert.assertEquals(expectedNews.getText(), actualNews.getText());
	}

	/**
	 * Gets all news in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllNews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		Set<News> allNews1 = dao.getAllNews();
		Set<News> allNews2 = new LinkedHashSet<News>();
		for (int i = 2016; i < 2050; i++) {
			allNews2.addAll(dao.getNewsByYear(i));
		}
		
		Assert.assertEquals(allNews1, allNews2);
		
	}
	
	/**
	 * Gets news by year and iterates over all news finding news of same year, 
	 * removing them from the first collection if found. The expected result is empty collection.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNewsByYear() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		Set<News> yearNews = dao.getNewsByYear(2016);
		Set<News> allNews = dao.getAllNews();
		Calendar calendar = Calendar.getInstance();
		for (News n : allNews) {
			calendar.setTime(n.getDate());
			if (calendar.get(Calendar.YEAR) == 2016) {
				yearNews.remove(n);
			}
		}
		
		Assert.assertTrue(yearNews.isEmpty());
	}
	
	/**
	 * Gets news by year and month and iterates over all news finding news of same year and month, 
	 * removing them from the first collection if found. The expected result is empty collection.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNewsByMonthAndYear() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO dao = daoFactory.getNewsDAO();
		
		Set<News> yearNews = dao.getNewsByMonthAndYear(8, 2016);
		Set<News> allNews = dao.getAllNews();
		Calendar calendar = Calendar.getInstance();
		for (News n : allNews) {
			
			calendar.setTime(n.getDate());
			if (calendar.get(Calendar.YEAR) == 2016 && calendar.get(Calendar.MONTH) == 8) {
				yearNews.remove(n);				
			}
		}
		
		Assert.assertTrue(yearNews.isEmpty());
	}
	
	/**
	 * Gets the amount of news in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfNews() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO newsDAO = daoFactory.getNewsDAO();
		
		int newsNum1 = newsDAO.getNumberOfNews();
		Set<News> allNews = newsDAO.getAllNews();
		int newsNum2 = allNews.size();
		
		Assert.assertEquals(newsNum1, newsNum2);
	}
	
	/**
	 * Gets the part of all news from the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllNewsPart() throws DAOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		INewsDAO newsDAO = daoFactory.getNewsDAO();
		
		Set<News> particularNews1 = newsDAO.getAllNewsPart(0, 6);
		List<News> allNews = new LinkedList<News>(newsDAO.getAllNews());
		Set<News> particularNews2 = new LinkedHashSet<News>(allNews.subList(0, 6));
		
		Assert.assertEquals(particularNews1, particularNews2);	
	}
}
