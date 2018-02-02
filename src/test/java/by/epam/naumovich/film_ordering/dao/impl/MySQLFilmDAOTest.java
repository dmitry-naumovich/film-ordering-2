package by.epam.naumovich.film_ordering.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.dao.IFilmDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;

/**
 * Tests DAO layer methods overridden in MySQLFilmDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * Database localization functionality is not tested here, so default language is passed as an arguments to all methods where it is required.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLFilmDAOTest {

	private IFilmDAO filmDAO;
	/**
	 * Database type used in this test suite.
	 * 
	 */
	private static final String MYSQL = "mysql";
	/**
	 * Language constant for passing to the service methods.
	 * 
	 */
	private static final String EN_LANG = "en";
	
	/**
	 * This object will be compared to the actual object taken from the DAO layer.
	 * 
	 */
	private Film expectedFilm;
	
	/**
	 * Executes every time before a single method call and initializes expected Film object.
	 * 
	 */
	@Before
	public void initTestFilm() {
		expectedFilm = new Film();
		expectedFilm.setId(1000);
		expectedFilm.setName("Test film name");
		expectedFilm.setYear(2016);
		expectedFilm.setDirector("Test film director");
		expectedFilm.setCountry("France");
		expectedFilm.setGenre("Thriller");
		expectedFilm.setLength(200);
		expectedFilm.setPrice(12f);
		expectedFilm.setRating(4f);
		expectedFilm.setActors("Test actors");
		expectedFilm.setComposer("Test composer");
		expectedFilm.setDescription("Test film description");
	}
	
	/**
	 * Adds expectedFilm to the data source via DAO layer, gets it back and compares two results.
	 * Tests if the film was correctly added.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void addFilm() throws DAOException {
//		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
//		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		Film film = filmDAO.save(expectedFilm);
        Film actualFilm = filmDAO.getById(film.getId(), EN_LANG);
        filmDAO.delete(film);
        
        Assert.assertEquals(expectedFilm.getName(), actualFilm.getName());
        Assert.assertEquals(expectedFilm.getYear(), actualFilm.getYear());
        Assert.assertEquals(expectedFilm.getDirector(), actualFilm.getDirector());
        Assert.assertEquals(expectedFilm.getCountry(), actualFilm.getCountry());
        Assert.assertEquals(expectedFilm.getGenre(), actualFilm.getGenre());
        Assert.assertEquals(expectedFilm.getLength(), actualFilm.getLength());
        Assert.assertEquals(expectedFilm.getPrice(), actualFilm.getPrice(), 0.01f);
        Assert.assertEquals(expectedFilm.getActors(), actualFilm.getActors());
        Assert.assertEquals(expectedFilm.getComposer(), actualFilm.getComposer());
        Assert.assertEquals(expectedFilm.getDescription(), actualFilm.getDescription());
	}

	/**
	 * Adds expectedFilm to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the film was correctly deleted.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void deleteFilm() throws DAOException {
//		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
//		//IFilmDAO filmDAO = daoFactory.getFilmDAO();

		Film film = filmDAO.save(expectedFilm);
		filmDAO.delete(film);
        Film deletedFilm = filmDAO.getById(film.getId(), EN_LANG);

        Assert.assertNull(deletedFilm);
	}

	/**
	 * Adds expectedFilm to the data source via DAO layer, edits it, gets it back and compares two results.
	 * Tests if the film was correctly edited.
	 * 
	 * @throws DAOException
	 * @throws InterruptedException 
	 */
	@Test
	public void updateFilm() throws DAOException, InterruptedException {
//		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
//		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		Film film = filmDAO.save(expectedFilm);
		expectedFilm.setName("test name 1");
		expectedFilm.setActors("test actors 1");
		expectedFilm.setPrice(12);
		filmDAO.save(expectedFilm);
		Film actualFilm = filmDAO.getById(film.getId(), EN_LANG);
        filmDAO.delete(film.getId());
	    
        Assert.assertEquals(expectedFilm.getName(), actualFilm.getName());
        Assert.assertEquals(expectedFilm.getPrice(), actualFilm.getPrice(), 0.01f);
        Assert.assertEquals(expectedFilm.getActors(), actualFilm.getActors());
	}
	
	/**
	 * Adds expectedFilm to the data source via DAO layer, gets it back by ID and compares two results.
	 * Tests if the valid film entity is returned by id.
	 * 
	 * @throws DAOException
	 */
	public void getFilmByID() throws DAOException {
//		DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
//		IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		Film film = filmDAO.save(expectedFilm);
		Film actualFilm = filmDAO.getById(film.getId(), EN_LANG);
		filmDAO.delete(film.getId());
		
		Assert.assertEquals(expectedFilm.getName(), actualFilm.getName());
        Assert.assertEquals(expectedFilm.getYear(), actualFilm.getYear());
        Assert.assertEquals(expectedFilm.getDirector(), actualFilm.getDirector());
        Assert.assertEquals(expectedFilm.getCountry(), actualFilm.getCountry());
        Assert.assertEquals(expectedFilm.getGenre(), actualFilm.getGenre());
        Assert.assertEquals(expectedFilm.getLength(), actualFilm.getLength());
        Assert.assertEquals(expectedFilm.getPrice(), actualFilm.getPrice(), 0.01f);
        Assert.assertEquals(expectedFilm.getActors(), actualFilm.getActors());
        Assert.assertEquals(expectedFilm.getComposer(), actualFilm.getComposer());
        Assert.assertEquals(expectedFilm.getDescription(), actualFilm.getDescription());
		
	}
	
	/**
	 * Adds expectedFilm to the data source via DAO layer, gets its name back by ID and compares two results.
	 * Tests if valid film name is returned by id.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getFilmNameByID() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		Film film = filmDAO.save(expectedFilm);
		String actualFilmName = filmDAO.getFilmNameByID(film.getId(), EN_LANG);
		filmDAO.delete(film.getId());
		
		Assert.assertEquals(expectedFilm.getName(), actualFilmName);
	}
	
	/**
	 * Gets all films in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllFilms() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		List<Film> allFilms1 = filmDAO.getAll(EN_LANG);
		List<Film> allFilms2 = filmDAO.getFilmsBetweenYears(1, 3000, EN_LANG);
		
		Assert.assertEquals(allFilms1, allFilms2);
	}

	/**
	 * Gets films by name in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getFilmsByName() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		Film film = filmDAO.save(expectedFilm);
		List<Film> filmsByName1 = filmDAO.getFilmsByName(expectedFilm.getName(), EN_LANG);
		
		List<Film> filmsByName2 = new ArrayList<Film>();
		for (Film f : filmDAO.getAll(EN_LANG)) {
			if (f.getName().toLowerCase().equals(expectedFilm.getName().toLowerCase())) {
				filmsByName2.add(f);
			}
		}
		
		filmDAO.delete(film.getId());
		Assert.assertEquals(filmsByName1, filmsByName2);
	}
	
	/**
	 * Gets films by year in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getFilmsByYear() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		List<Film> filmsByYear1 = filmDAO.getFilmsByYear(2012, EN_LANG);
		
		List<Film> filmsByYear2 = new ArrayList<Film>();
		for (Film f : filmDAO.getAll(EN_LANG)) {
			if (f.getYear() == 2012) {
				filmsByYear2.add(f);
			}
		}
		
		Assert.assertEquals(filmsByYear1, filmsByYear2);
	}
	
	/**
	 * Gets films by genre in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getFilmsByGenre() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		List<Film> filmsByGenre1 = filmDAO.getFilmsByGenre(expectedFilm.getGenre(), EN_LANG);
		
		List<Film> filmsByGenre2 = new ArrayList<Film>();
		for (Film f : filmDAO.getAll(EN_LANG)) {
			if (f.getGenre().toLowerCase().contains(expectedFilm.getGenre().toLowerCase())) {
				filmsByGenre2.add(f);
			}
		}
		Assert.assertEquals(filmsByGenre1, filmsByGenre2);
	}
	
	/**
	 * Gets films by country in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getFilmsByCountry() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		List<Film> filmsByCountry1 = filmDAO.getFilmsByCountry(expectedFilm.getCountry(), EN_LANG);
		
		List<Film> filmsByCountry2 = new ArrayList<Film>();
		for (Film f : filmDAO.getAll(EN_LANG)) {
			if (f.getCountry().toLowerCase().contains(expectedFilm.getCountry().toLowerCase())) {
				filmsByCountry2.add(f);
			}
		}
		Assert.assertEquals(filmsByCountry1, filmsByCountry2);
	}
	
	/**
	 * Gets films between years in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getFilmsBetweenYears() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		List<Film> filmsByYears1 = filmDAO.getFilmsBetweenYears(2000, 2005, EN_LANG);
		
		List<Film> filmsByYears2 = new ArrayList<Film>();
		for (Film f : filmDAO.getAll(EN_LANG)) {
			if (f.getYear() >= 2000 && f.getYear() <= 2005) {
				filmsByYears2.add(f);
			}
		}
		Assert.assertEquals(filmsByYears1, filmsByYears2);
	}
	
	/**
	 * Gets the amount of films in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfFilms() throws DAOException {
		int filmsNum1 = (int) filmDAO.count();
		List<Film> allFilms = filmDAO.getAll(EN_LANG);
		int filmsNum2 = allFilms.size();
		
		Assert.assertEquals(filmsNum1, filmsNum2);
	}
	
	/**
	 * Gets the part of all films from the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllFilmsPart() throws DAOException {
		//DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
		//IFilmDAO filmDAO = daoFactory.getFilmDAO();
		
		List<Film> particularFilms1 = filmDAO.getAllPart(0, 6, EN_LANG);
		List<Film> allFilms = new ArrayList<Film>(filmDAO.getAll(EN_LANG));
		List<Film> particularFilms2 = new ArrayList<Film>(allFilms.subList(0, 6));
		
		Assert.assertEquals(particularFilms1, particularFilms2);
		
	}
}
