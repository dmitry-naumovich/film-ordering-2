package by.epam.naumovich.film_ordering.dao;

import java.util.List;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines methods for implementing in the DAO layer for the Film entity.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Transactional
public interface IFilmDAO extends CrudRepository<Film, Integer> {

    /**
     * Adds a new film to the data source
     *
     * @param film new film entity
     * @return ID of a newly added film or 0 if it was not added
     * @throws DAOException
     */
	//int create(Film film) throws DAOException;
	
	/**
	 * Deletes a film from the data source
	 * 
	 * @param id ID of a film which will be deleted
	 * @throws DAOException
	 */
	//void delete(int id) throws DAOException;
	
//	/**
//	 * Edits a film in the data source
//	 *
//	 * @param id ID of a film which will be edited
//	 * @param editedFilm film entity with edited fields
//	 * @throws DAOException
//	 */
//	//todo: save from CrudRepository updates also
//    @Query(value = "UPDATE films SET f_name = ?, f_year = ?, f_direct = ?, f_country = ?, f_genre = ?, f_actors = ?, f_composer = ?, f_description = ?, f_length = ?, f_price = ? WHERE f_id = ?", nativeQuery = true)
//	void update(@Param("id") int id, @Param("editedFilm") Film editedFilm) throws DAOException;
	
	/**
	 * Searches for a film in the data source by its ID considering language
	 * 
	 * @param id ID of a film
	 * @return found film or null if it was not found
	 * @throws DAOException
	 */
    @Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE f.f_id = :id", nativeQuery = true)
	Film getById(@Param("id") int id, @Param("lang") String lang) throws DAOException;

	/**
	 * Returns film name by its ID
	 * 
	 * @param id ID of a film
	 * @param lang language of the source data to be returned
	 * @return the name of the film or null if it was not found
	 * @throws DAOException
	 */
	@Query(value = "SELECT COALESCE(lf.loc_name, f.f_name)" +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE f.f_id = :id", nativeQuery = true)
	String getFilmNameByID(@Param("id") int id, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Returns twelve last added to the data source films
	 * 
	 * @param lang language of the source data to be returned
	 * @return a set of films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "ORDER BY f.f_id DESC LIMIT 12", nativeQuery = true)
	List<Film> getTwelveLastAddedFilms(@Param("lang") String lang) throws DAOException;
	
	/**
	 * Returns all films that a present in the data source
	 * 
	 * @param lang language of the source data to be returned
	 * @return a set of all films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "ORDER BY f_rating DESC", nativeQuery = true)
	List<Film> getAll(@Param("lang") String lang) throws DAOException;
	
	/**
	 * Returns a necessary part of all films from the data source
	 * 
	 * @param start start index of necessary film part
	 * @param amount amount of films to be returned
	 * @param lang language of the source data to be returned
	 * @return a part of the set of all films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year," +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "ORDER BY f_rating DESC LIMIT :start, :amount", nativeQuery = true)
	List<Film> getAllPart(@Param("start") int start, @Param("amount") int amount, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by name 
	 * 
	 * @param name film name
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE f.f_name = :name OR lf.loc_name = :name", nativeQuery = true)
	List<Film> getFilmsByName(@Param("name") String name, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by year
	 * 
	 * @param year film year
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE f.f_year = :year", nativeQuery = true)
	List<Film> getFilmsByYear(@Param("year") int year, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by genre
	 * 
	 * @param genre film genre
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE FIND_IN_SET(:genre, f.f_genre) > 0 OR FIND_IN_SET(:genre, lf.loc_genre) > 0", nativeQuery = true)
	List<Film> getFilmsByGenre(@Param("genre") String genre, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by country
	 * 
	 * @param country film country
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE FIND_IN_SET(:country, f.f_country) > 0 OR FIND_IN_SET(:country, lf.loc_country) > 0", nativeQuery = true)
	List<Film> getFilmsByCountry(@Param("country") String country, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Searches for films in the data source by year range
	 * 
	 * @param yearFrom left border of the range (including)
	 * @param yearTo right border of the range (including)
	 * @param lang language of the source data to be returned
	 * @return a set of found films
	 * @throws DAOException
	 */
	@Query(value = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS f_name, f.f_year, " +
            "COALESCE(lf.loc_direct, f.f_direct) AS f_direct, COALESCE(lf.loc_country, f.f_country) AS f_country, " +
            "COALESCE(lf.loc_genre, f.f_genre) AS f_genre, COALESCE(lf.loc_actors, f.f_actors) AS f_actors, " +
            "COALESCE(lf.loc_composer, f.f_composer) AS f_composer, " +
            "COALESCE(lf.loc_description, f.f_description) AS f_description, f.f_length, f.f_rating, f.f_price " +
            "FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = :lang) AS lf ON f.f_id = lf.loc_id " +
            "WHERE f.f_year >= :yearFrom AND f.f_year <= :yearTo", nativeQuery = true)
	List<Film> getFilmsBetweenYears(@Param("yearFrom") int yearFrom, @Param("yearTo") int yearTo, @Param("lang") String lang) throws DAOException;
	
	/**
	 * Returns all film genres that are present in the data source
	 * 
	 * @return an array of available genres
	 * @param lang language of the source data to be returned
	 * @throws DAOException
	 */
	@Query(value = "SHOW COLUMNS FROM films LIKE 'f_genre'", nativeQuery = true)
	String[] getAvailableGenresDefault() throws DAOException;

    /**
     * Returns all film genres that are present in the data source
     *
     * @return an array of available genres
     * @param lang language of the source data to be returned
     * @throws DAOException
     */
    @Query(value = "SHOW COLUMNS FROM films_local LIKE 'loc_genre'", nativeQuery = true)
    String[] getAvailableGenresLocalized() throws DAOException;
	
	/**
	 * Returns all film countries that are present in the data source
	 * 
	 * @return an array of available countries
	 * @param lang language of the source data to be returned
	 * @throws DAOException
	 */
	@Query(value = "SHOW COLUMNS FROM films LIKE 'f_country'", nativeQuery = true)
    String[] getAvailableCountriesDefault() throws DAOException;

	/**
	 * Returns all film countries that are present in the data source
	 *
	 * @return an array of available countries
	 * @param lang language of the source data to be returned
	 * @throws DAOException
	 */
	@Query(value = "SHOW COLUMNS FROM films_local LIKE 'loc_country'", nativeQuery = true)
    String[] getAvailableCountriesLocalized() throws DAOException;

}
