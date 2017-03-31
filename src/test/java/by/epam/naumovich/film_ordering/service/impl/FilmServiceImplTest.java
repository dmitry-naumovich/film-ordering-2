package by.epam.naumovich.film_ordering.service.impl;

import org.junit.Test;

import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.AddFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.EditFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;

/**
 * Tests service layer methods overridden in FilmServiceImpl class in a way of passing invalid parameters into service methods
 * and expecting exceptions on the output with the help of JUnit 4 framework.
 * Database localization functionality is not tested here, so default language is passed as an arguments to all methods where it is required.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class FilmServiceImplTest {

	/**
	 * Language constant for passing to the service methods.
	 * 
	 */
	private static final String EN_LANG = "en";
	
	/**
	 * Tries to add new film with invalid length value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddFilmServiceException.class)
	public void addNewFilmWithWrongLength() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.addNewFilm("testname", "2016", "testdirector", "testcast", null, null, null, "-120", "-120", "testdescription");		
	}
	
	/**
	 * Tries to add new film with invalid year value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=AddFilmServiceException.class)
	public void addNewFilmWithWrongYear() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.addNewFilm("testname", "abc1", "testdirector", "testcast", null, null, null, "120", "10", "testdescription");		
	}
	
	/**
	 * Tries to add new film with invalid genre list value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void addNewFilmWithWrongGenre() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.addNewFilm("testname", "1990", "testdirector", "testcast", null, null, new String[]{"UK", "Bilarus"}, "120", "10", "testdescription");		
	}
	
	/**
	 * Tries to delete film by invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void deleteFilm() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.deleteFilm(0);
	}
	
	/**
	 * Tries to edit the film with invalid (null) director value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=EditFilmServiceException.class)
	public void editFilm() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.editFilm(1, "testname", "2010", null, null, null, null, null, "102", "1", null);
	}
	

	/**
	 * Tries to get the film by invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetFilmServiceException.class)
	public void getFilmByID() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.getFilmByID(-1, EN_LANG);
	}
	
	/**
	 * Tries to get the film name by invalid ID value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void getFilmNameByID() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.getFilmNameByID(0, EN_LANG);
	}
	
	/**
	 * Tries to search for films by null input String value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetFilmServiceException.class)
	public void searchByNullName() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.searchByName(null, EN_LANG);
	}
	
	/**
	 * Tries to search for films by the senseless input String value and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetFilmServiceException.class)
	public void searchBySenselessName() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.searchByName("adasdasd;lfsdfmw;lefw;emfw;;", EN_LANG);
	}
	
	/**
	 * Tries to widened search for films by the period when no films were produced and expects for the exception.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetFilmServiceException.class)
	public void searchWidened() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.searchWidened("The", "1600", "1700", null, null, EN_LANG);
	}
	
	/**
	 * Tries to get films from the DAO layer by the invalid page number.
	 * 
	 * @throws ServiceException
	 */
	@Test(expected=GetFilmServiceException.class)
	public void getAllFilmsPart() throws ServiceException {
		IFilmService service = ServiceFactory.getInstance().getFilmService();
		service.getAllFilmsPart(0, EN_LANG);
	}
}
