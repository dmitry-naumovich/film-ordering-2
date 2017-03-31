package by.epam.naumovich.film_ordering.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.dao.DAOFactory;
import by.epam.naumovich.film_ordering.dao.IFilmDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.AddFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.EditFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;

/**
 * IFilmService interface implementation that works with IFilmDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class FilmServiceImpl implements IFilmService {

	private static final String MYSQL = "mysql";
	private static final String SPACE = " ";
	private static final int FILMS_AMOUNT_ON_PAGE = 10;

	@Override
	public int addNewFilm(String name, String year, String director, String cast, String[] countries, String composer,
			String[] genres, String length, String price, String description) throws ServiceException {
		
		if (!Validator.validateStrings(name, year, director, length, price)) {
			throw new AddFilmServiceException(ExceptionMessages.CORRUPTED_FILM_REQUIRED_FIELDS);
		}
		Film newFilm = new Film();
		newFilm.setName(name);
		
		try {
			int fYear = Integer.parseInt(year);
			if (fYear < 0) {
				throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR);
			}
			newFilm.setYear(fYear);
		} catch (NumberFormatException e) {
			throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR);
		}
		
		newFilm.setDirector(director);
		if (Validator.validateStrings(cast)) { 
			 newFilm.setActors(cast);
		}
		
		if (Validator.validateObject(countries) && Validator.validateStringArray(countries)) {
			StringBuilder countryBuilder = new StringBuilder();
			for (String s : countries) {
				countryBuilder.append(s + ",");
			}
			countryBuilder.deleteCharAt(countryBuilder.length() - 1);
			newFilm.setCountry(countryBuilder.toString());
		}
		if (Validator.validateStrings(composer)) {
			newFilm.setComposer(composer);
		}
		if (Validator.validateObject(genres) && Validator.validateStringArray(genres)) {
			StringBuilder genresBuilder = new StringBuilder();
			for (String g : genres) {
				genresBuilder.append(g + ",");
			}
			genresBuilder.deleteCharAt(genresBuilder.length() - 1);
			newFilm.setGenre(genresBuilder.toString());
		}
		
		try {
			int fLength = Integer.parseInt(length);
			if (fLength < 0) {
				throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_LENGTH);
			}
			newFilm.setLength(fLength);
		} catch (NumberFormatException e) {
			throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_LENGTH);
		}
		
		try {
			float fPrice = Float.parseFloat(price);
			if (fPrice < 0) {
				throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_PRICE);
			}
			newFilm.setPrice(fPrice);
		} catch (NumberFormatException e) {
			throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_PRICE);
		}
	
		if (Validator.validateStrings(description)) {
			newFilm.setDescription(description);
		}
		
		int newFilmID = 0;
		try {
			IFilmDAO filmDAO = DAOFactory.getDAOFactory(MYSQL).getFilmDAO();
			newFilmID = filmDAO.addFilm(newFilm);
			if (newFilmID == 0) {
				throw new AddFilmServiceException(ExceptionMessages.FILM_NOT_ADDED);
			}
			newFilm.setId(newFilmID);
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return newFilmID;
	}
	
	@Override
	public void deleteFilm(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			filmDAO.deleteFilm(id);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}	
	}

	@Override
	public void editFilm(int id, String name, String year, String director, String cast, String[] countries,
			String composer, String[] genres, String length, String price, String description) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new EditFilmServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		if (!Validator.validateStrings(name, year, director, length, price)) {
			throw new EditFilmServiceException(ExceptionMessages.CORRUPTED_FILM_REQUIRED_FIELDS);
		}
		Film editedFilm = new Film();
		editedFilm.setId(id);
		editedFilm.setName(name);
		
		try {
			int fYear = Integer.parseInt(year);
			if (fYear < 0) {
				throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR);
			}
			editedFilm.setYear(fYear);
		} catch (NumberFormatException e) {
			throw new EditFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR);
		}
		
		editedFilm.setDirector(director);
		if (Validator.validateStrings(cast)) { 
			 editedFilm.setActors(cast);
		}
		
		if (Validator.validateObject(countries) && Validator.validateStringArray(countries)) {
			StringBuilder countryBuilder = new StringBuilder();
			for (String s : countries) {
				countryBuilder.append(s + ",");
			}
			countryBuilder.deleteCharAt(countryBuilder.length() - 1);
			editedFilm.setCountry(countryBuilder.toString());
		}
		if (Validator.validateStrings(composer)) {
			editedFilm.setComposer(composer);
		}
		if (Validator.validateObject(genres) && Validator.validateStringArray(genres)) {
			StringBuilder genresBuilder = new StringBuilder();
			for (String g : genres) {
				genresBuilder.append(g + ",");
			}
			genresBuilder.deleteCharAt(genresBuilder.length() - 1);
			editedFilm.setGenre(genresBuilder.toString());
		}
		
		try {
			int fLength = Integer.parseInt(length);
			if (fLength < 0) {
				throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_LENGTH);
			}
			editedFilm.setLength(fLength);
		} catch (NumberFormatException e) {
			throw new EditFilmServiceException(ExceptionMessages.INVALID_FILM_LENGTH);
		}
		
		try {
			float fPrice = Float.parseFloat(price);
			if (fPrice < 0) {
				throw new AddFilmServiceException(ExceptionMessages.INVALID_FILM_PRICE);
			}
			editedFilm.setPrice(fPrice);
		} catch (NumberFormatException e) {
			throw new EditFilmServiceException(ExceptionMessages.INVALID_FILM_PRICE);
		}
	
		if (Validator.validateStrings(description)) {
			editedFilm.setDescription(description);
		}
		
		try {
			IFilmDAO filmDAO = DAOFactory.getDAOFactory(MYSQL).getFilmDAO();
			filmDAO.editFilm(id, editedFilm);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}
	
	@Override
	public Set<Film> getTwelveLastAddedFilms(String lang) throws ServiceException {
		Set<Film> filmSet = new LinkedHashSet<Film>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			filmSet = filmDAO.getTwelveLastAddedFilms(lang);
			
			if (filmSet.isEmpty()) {
				throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_IN_DB);
			}
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return filmSet;
	}

	@Override
	public Set<Film> getAllFilms(String lang) throws ServiceException {
		Set<Film> filmSet = new LinkedHashSet<Film>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			filmSet = filmDAO.getAllFilms(lang);
			
			if (filmSet.isEmpty()) {
				throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_IN_DB);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return filmSet;
	}

	@Override
	public Film getFilmByID(int id, String lang) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new GetFilmServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		Film film = null;
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			film = filmDAO.getFilmByID(id, lang);
			if (film == null) {
				throw new GetFilmServiceException(ExceptionMessages.FILM_NOT_PRESENT);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return film;
	}
	

	@Override
	public String getFilmNameByID(int id, String lang) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			String name = filmDAO.getFilmNameByID(id, lang);
			if (name == null) {
				throw new GetFilmServiceException(ExceptionMessages.FILM_NOT_PRESENT);
			}
			return name;
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	

	@Override
	public Set<Film> searchByName(String text, String lang) throws ServiceException {
		if (!Validator.validateStrings(text)) {
			throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_FOUND);
		}
		
		Set<Film> foundFilms = new LinkedHashSet<Film>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			foundFilms.addAll(filmDAO.getFilmsByName(text, lang));
			Set<Film> allFilms = filmDAO.getAllFilms(lang);
			
			String searchText = text.toLowerCase();
			boolean moreThanOneWord = false;
			String[] words = null;
			
			if (searchText.split(FilmServiceImpl.SPACE).length > 1) {
				moreThanOneWord = true;
				words = searchText.split(FilmServiceImpl.SPACE);
			}
			for (Film f : allFilms) {
				String filmName = f.getName().toLowerCase();
				if (filmName.contains(searchText) || searchText.contains(filmName)) {
					foundFilms.add(f);
				}
				if (moreThanOneWord) {
					for (String s : words) {
						if (filmName.contains(s) || s.contains(filmName)) {
							foundFilms.add(f);
						}
					}
				}
			}
			
			if (foundFilms.isEmpty()) {
				throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_FOUND);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return foundFilms;
	}

	@Override
	public Set<Film> searchWidened(String name, String yearFrom, String yearTo, String[] genres, String[] countries, String lang) throws ServiceException {
		int fYearFrom = 0;
		int fYearTo = 9999;
		
		if (Validator.validateStrings(yearFrom)) {
			try {
				fYearFrom = Integer.parseInt(yearFrom);
				if (fYearFrom < 0) {
					throw new GetFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR);
				}
				
			} catch (NumberFormatException e) {
				throw new GetFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR, e);
			}
		}
		
		if (Validator.validateStrings(yearTo)) {
			try {
				fYearTo = Integer.parseInt(yearTo);
				if (fYearTo < 0) {
					throw new GetFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR);
				}
				
			} catch (NumberFormatException e) {
				throw new GetFilmServiceException(ExceptionMessages.INVALID_FILM_YEAR, e);
			}
		}
		
		Set<Film> foundFilms = new LinkedHashSet<Film>();
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			
			foundFilms.addAll(filmDAO.getFilmsBetweenYears(fYearFrom, fYearTo, lang));
			
			if (Validator.validateObject(genres) && Validator.validateStringArray(genres)) {
				Set<Film> filmsByGenre = new LinkedHashSet<Film>();
				for (String genre : genres) {
					filmsByGenre = filmDAO.getFilmsByGenre(genre, lang);
					foundFilms.retainAll(filmsByGenre);
				}
			}
			
			if (Validator.validateObject(countries) && Validator.validateStringArray(countries)) {
				Set<Film> filmsByCountry = new LinkedHashSet<Film>();
				for (String country : countries) {
					filmsByCountry = filmDAO.getFilmsByCountry(country, lang);
					foundFilms.retainAll(filmsByCountry);
				}
			}
			
			if (Validator.validateStrings(name)) {
				try {
					Set<Film> filmsByName = searchByName(name, lang);
					foundFilms.retainAll(filmsByName);
				} catch (GetFilmServiceException e) {
				}
			}
			
			if (foundFilms.isEmpty()) {
				throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_FOUND);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return foundFilms;
	}

	@Override
	public String[] getAvailableGenres(String lang) throws ServiceException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			String[] genres = filmDAO.getAvailableGenres(lang);
			if (genres == null) {
				throw new ServiceException(ExceptionMessages.GENRES_NOT_AVAILABLE);
			}
			return genres;
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public String[] getAvailableCountries(String lang) throws ServiceException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			String[] countries = filmDAO.getAvailableCountries(lang);
			if (countries == null) {
				throw new ServiceException(ExceptionMessages.COUNTRIES_NOT_AVAILABLE);
			}
			return countries;
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public Set<Film> getAllFilmsPart(int pageNum, String lang) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetFilmServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		int start = (pageNum - 1) * FILMS_AMOUNT_ON_PAGE;
		
		Set<Film> filmSet = new LinkedHashSet<Film>();		
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			filmSet = filmDAO.getAllFilmsPart(start, FILMS_AMOUNT_ON_PAGE, lang);
			
			if (filmSet.isEmpty()) {
				throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_IN_DB);
			}
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
		
		return filmSet;
	}

	@Override
	public int getNumberOfAllFilmsPages() throws ServiceException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(MYSQL);
			IFilmDAO filmDAO = daoFactory.getFilmDAO();
			int numOfFilms = filmDAO.getNumberOfFilms();
			if (numOfFilms % FILMS_AMOUNT_ON_PAGE == 0) {
				return numOfFilms / FILMS_AMOUNT_ON_PAGE;
			}
			else {
				return numOfFilms / FILMS_AMOUNT_ON_PAGE + 1;
			}
			
			
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}
}
