package by.epam.naumovich.film_ordering.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.dao.IFilmDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.AddFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.EditFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ENG_LANG;

/**
 * IFilmService interface implementation that works with IFilmDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Service
public class FilmServiceImpl implements IFilmService {

	private static final String SPACE = " ";
	private static final int FILMS_AMOUNT_ON_PAGE = 10;

	private final IFilmDAO filmDAO;

	@Autowired
    public FilmServiceImpl(IFilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

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

        if (Validator.validateStrings(composer)) {
            newFilm.setComposer(composer);
        }

        newFilm = validateAndSetCountries(newFilm, countries);
		newFilm = validateAndSetGenres(newFilm, genres);

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

        Film film = filmDAO.save(newFilm);
        if (film.getId() == 0) {
            throw new AddFilmServiceException(ExceptionMessages.FILM_NOT_ADDED);
        }
        return film.getId();
	}
	
	@Override
	public void deleteFilm(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		filmDAO.delete(id);
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
        if (Validator.validateStrings(composer)) {
            editedFilm.setComposer(composer);
        }

        editedFilm = validateAndSetCountries(editedFilm, countries);
        editedFilm = validateAndSetGenres(editedFilm, genres);
		
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

		filmDAO.save(editedFilm);
	}
	
	@Override
	public List<Film> getTwelveLastAddedFilms(String lang) throws ServiceException {
		List<Film> filmSet;
		try {
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
	public List<Film> getAllFilms(String lang) throws ServiceException {
		List<Film> filmSet;
		try {
			filmSet = filmDAO.getAll(lang);
			
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
		Film film;
		try {
			film = filmDAO.getById(id, lang);
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
	public List<Film> searchByName(String text, String lang) throws ServiceException {
		if (!Validator.validateStrings(text)) {
			throw new GetFilmServiceException(ExceptionMessages.NO_FILMS_FOUND);
		}
		
		List<Film> foundFilms = new ArrayList<>();
		try {
			foundFilms.addAll(filmDAO.getFilmsByName(text, lang));
			List<Film> allFilms = filmDAO.getAll(lang);
			
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
	public List<Film> searchWidened(String name, String yearFrom, String yearTo, String[] genres, String[] countries, String lang) throws ServiceException {
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
		
		List<Film> foundFilms = new ArrayList<>();
		try {
			foundFilms.addAll(filmDAO.getFilmsBetweenYears(fYearFrom, fYearTo, lang));
			
			if (Validator.validateObject(genres) && Validator.validateStringArray(genres)) {
				List<Film> filmsByGenre;
				for (String genre : genres) {
					filmsByGenre = filmDAO.getFilmsByGenre(genre, lang);
					foundFilms.retainAll(filmsByGenre);
				}
			}
			
			if (Validator.validateObject(countries) && Validator.validateStringArray(countries)) {
				List<Film> filmsByCountry;
				for (String country : countries) {
					filmsByCountry = filmDAO.getFilmsByCountry(country, lang);
					foundFilms.retainAll(filmsByCountry);
				}
			}
			
			if (Validator.validateStrings(name)) {
				try {
					List<Film> filmsByName = searchByName(name, lang);
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
			String[] genres;
			if (lang.equals(ENG_LANG)) {
			    genres = filmDAO.getAvailableGenresDefault();
            } else {
			    genres = filmDAO.getAvailableGenresLocalized();
            }
			if (genres == null) {
				throw new ServiceException(ExceptionMessages.GENRES_NOT_AVAILABLE);
			}
			String genresE = genres[0];
			int f = genresE.indexOf("('");
			int l = genresE.lastIndexOf("')");
			String ss = genresE.substring(f + 2, l);
			return ss.split("','");
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public String[] getAvailableCountries(String lang) throws ServiceException {
		try {
			String[] countries;
			if (lang.equals(ENG_LANG)) {
			    countries = filmDAO.getAvailableCountriesDefault();
            } else {
			    countries = filmDAO.getAvailableCountriesLocalized();
            }
			if (countries == null) {
				throw new ServiceException(ExceptionMessages.COUNTRIES_NOT_AVAILABLE);
			}
            String countriesE = countries[0];
            int f = countriesE.indexOf("('");
            int l = countriesE.lastIndexOf("')");
            String ss = countriesE.substring(f + 2, l);
            return ss.split("','");
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessages.SOURCE_ERROR, e);
		}
	}

	@Override
	public List<Film> getAllFilmsPart(int pageNum, String lang) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetFilmServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		int start = (pageNum - 1) * FILMS_AMOUNT_ON_PAGE;
		
		List<Film> filmSet;
		try {
			filmSet = filmDAO.getAllPart(start, FILMS_AMOUNT_ON_PAGE, lang);
			
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
		int numOfFilms = (int)filmDAO.count(); //todo: return long everywhere
		if (numOfFilms % FILMS_AMOUNT_ON_PAGE == 0) {
			return numOfFilms / FILMS_AMOUNT_ON_PAGE;
		}
		else {
			return numOfFilms / FILMS_AMOUNT_ON_PAGE + 1;
		}
	}

    private Film validateAndSetCountries(Film film, String[] countries) {
        String countriesString = validateAndBuildStringFromArray(countries);
	    if (countriesString != null) {
            film.setCountry(countriesString);
        }
        return film;
    }

    private Film validateAndSetGenres(Film film, String[] genres) {
        String genresString = validateAndBuildStringFromArray(genres);
        if (genresString != null) {
            film.setGenre(genresString);
        }
        return film;
    }

    private String validateAndBuildStringFromArray(String[] array) {
        if (Validator.validateObject(array) && Validator.validateStringArray(array)) {
            StringBuilder sb = new StringBuilder();
            for (String g : array) {
                sb.append(g).append(",");
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        return null;
    }

}
