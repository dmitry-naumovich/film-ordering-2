package by.epam.naumovich.film_ordering.service.impl;

import org.junit.Test;

import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.AddNewsServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.EditNewsServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.GetNewsServiceException;

/**
 * Tests service layer methods overridden in NewsServiceImpl class in a way of passing invalid parameters into service methods
 * and expecting exceptions on the output with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class NewsServiceImplTest {

	private INewsService service;

	/**
	 * Tries to add new news with invalid news title value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddNewsServiceException.class)
	public void addNewsWithInvalidTitle() throws ServiceException {
		service.create(null, "test news text");
	}
	
	/**
	 * Tries to add new news with invalid news text value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddNewsServiceException.class)
	public void addNewsWithInvalidText() throws ServiceException {
		service.create("test news title", "");
	}
	
	/**
	 * Tries to delete news by invalid ID value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void deleteNews() throws ServiceException {
		service.delete(0);
	}
	
	/**
	 * Tries to edit the news with invalid news ID value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void editNewsWithInvalidID() throws ServiceException {
		service.update(0, "new title", "new text");
	}
	
	/**
	 * Tries to edit the news with invalid news title and text value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=EditNewsServiceException.class)
	public void editNewsWithInvalidTextAndTitle() throws ServiceException {
		service.update(1, "", null);
	}
	

	/**
	 * Tries to get the news by the invalid ID value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetNewsServiceException.class)
	public void getNewsById() throws ServiceException {
		service.getById(-1);
	}
	
	/**
	 * Tries to get the news by the incorrect year value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetNewsServiceException.class)
	public void getNewsByYear() throws ServiceException {
		service.getByYear(1001);
	}
	
	/**
	 * Tries to get the news by the incorrect month value and expects for the exception. 
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetNewsServiceException.class)
	public void getNewsByMonth() throws ServiceException {
		service.getByMonth(13, 2012);
	}
	
	/**
	 * Tries to get news from the DAO layer by the invalid page number.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetNewsServiceException.class)
	public void getAllNewsPart() throws ServiceException {
		service.getAllPart(0);
	}
}
