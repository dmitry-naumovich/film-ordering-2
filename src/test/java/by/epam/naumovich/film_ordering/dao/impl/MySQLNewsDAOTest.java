package by.epam.naumovich.film_ordering.dao.impl;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.dao.INewsDAO;
import com.google.common.collect.Iterables;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

/**
 * Tests DAO layer methods overridden in MySQLNewsDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLNewsDAOTest {

	private INewsDAO dao;

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
	 */
	@Test
	public void addNews() {
		int id = dao.save(expectedNews).getId();
		News actualNews = dao.findOne(id);
		dao.delete(id);
		
		Assert.assertEquals(expectedNews.getDate(), actualNews.getDate());
		Assert.assertEquals(expectedNews.getTime(), actualNews.getTime());
		Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());	
		Assert.assertEquals(expectedNews.getText(), actualNews.getText());
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the news was correctly deleted.
	 */
	@Test
	public void deleteNews() {
		int id = dao.save(expectedNews).getId();
		dao.delete(id);
		News actualNews = dao.findOne(id);
		
		Assert.assertNull(actualNews);
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, edits it, gets it back and compares two results.
	 * Tests if the news was correctly edited.
	 */
	@Test
	public void editNews() {
		int id = dao.save(expectedNews).getId();
		expectedNews.setId(id);
		expectedNews.setTitle("updated title");
		expectedNews.setText("updated news text");
		dao.save(expectedNews);
		News actualNews = dao.findOne(id);
		dao.delete(id);
		
		Assert.assertEquals(expectedNews.getDate(), actualNews.getDate());
		Assert.assertEquals(expectedNews.getTime(), actualNews.getTime());
		Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());	
		Assert.assertEquals(expectedNews.getText(), actualNews.getText());
	}
	
	/**
	 * Adds expectedNews to the data source via DAO layer, gets it back by ID and compares two results.
	 * Tests if valid news entity is returned by id.
	 */
	@Test
	public void getNewsById() {
		int id = dao.save(expectedNews).getId();
		News actualNews = dao.findOne(id);
		dao.delete(id);
		
		Assert.assertEquals(expectedNews.getDate(), actualNews.getDate());
		Assert.assertEquals(expectedNews.getTime(), actualNews.getTime());
		Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());	
		Assert.assertEquals(expectedNews.getText(), actualNews.getText());
	}
	
	/**
	 * Gets news by year and iterates over all news finding news of same year, 
	 * removing them from the first collection if found. The expected result is empty collection.
	 */
	@Test
	public void getNewsByYear()  {
		List<News> yearNews = dao.findByYear(2016);
		Iterable<News> allNews = dao.findAll();
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
	 */
	@Test
	public void getNewsByMonthAndYear()  {
		List<News> yearNews = dao.findByMonthAndYear(8, 2016);
		Iterable<News> allNews = dao.findAll();
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
	 */
	@Test
	public void getNumberOfNews()  {
		int newsNum1 = (int)dao.count();
		Iterable<News> allNews = dao.findAll();
		int newsNum2 = Iterables.size(allNews);
		
		Assert.assertEquals(newsNum1, newsNum2);
	}
	
	/**
	 * Gets the part of all news from the data source in two different ways and compares the results which must be equal.
	 */
	@Test
	public void getAllNewsPart()  {
		Iterable<News> particularNews1 = dao.findAll(new PageRequest(0, 6));
		Iterable<News> allNews = dao.findAll();
		Iterable<News> particualNews2 = Iterables.partition(allNews, 6).iterator().next();

		Assert.assertEquals(particularNews1, particualNews2);
	}
}
