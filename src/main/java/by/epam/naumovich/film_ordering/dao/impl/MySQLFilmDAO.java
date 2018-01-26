package by.epam.naumovich.film_ordering.dao.impl;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.List;
//
//import by.epam.naumovich.film_ordering.bean.Film;
//import by.epam.naumovich.film_ordering.dao.IFilmDAO;
//import by.epam.naumovich.film_ordering.dao.exception.DAOException;
//import by.epam.naumovich.film_ordering.dao.pool.ConnectionPool;
//import by.epam.naumovich.film_ordering.dao.pool.exception.ConnectionPoolException;
//import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;
//
///**
// * IFilmDAO interface implementation that works with MySQL database
// *
// * @author Dmitry Naumovich
// * @version 1.0
// */
public class MySQLFilmDAO /*implements IFilmDAO*/ {
//
//	public static final String INSERT_NEW_FILM = "INSERT INTO films (f_name, f_year, f_direct, f_country, f_genre, f_actors, f_composer, f_description, f_length, f_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//	public static final String DELETE_FILM = "DELETE FROM films WHERE f_id = ?";
//	public static final String UPDATE_FILM_BY_ID = "UPDATE films SET f_name = ?, f_year = ?, f_direct = ?, f_country = ?, f_genre = ?, f_actors = ?, f_composer = ?, f_description = ?, f_length = ?, f_price = ? WHERE f_id = ?";
//
//	public static final String SELECT_FILM_BY_ID = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id	WHERE f.f_id = ?";
//	public static final String SELECT_FILM_NAME_BY_ID = "SELECT COALESCE(lf.loc_name, f.f_name) FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id WHERE f.f_id = ?";
//	public static final String SELECT_NEW_FILM_ID = "SELECT f_id FROM films WHERE f_name = ? AND f_year = ? AND f_direct = ? AND f_length = ?";
//
//	public static final String SELECT_ALL_FILMS_ORDERED_BY_RATING = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id ORDER BY rating DESC";
//	public static final String SELECT_ALL_FILMS_PART_ORDERED_BY_RATING = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id ORDER BY rating DESC LIMIT ?, ?";
//	public static final String SELECT_TWELVE_LAST_ADDED_FILMS = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id ORDER BY f.f_id DESC LIMIT 12";
//	public static final String SELECT_ALL_FILMS_COUNT = "SELECT COUNT(*) FROM films";
//
//	public static final String SELECT_FILMS_BY_NAME = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id WHERE f.f_name = ? OR lf.loc_name = ?";
//	public static final String SELECT_FILMS_BY_YEAR = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id WHERE f.f_year = ?";
//
//	public static final String SELECT_FILMS_BY_GENRE = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id WHERE FIND_IN_SET(?, f.f_genre) > 0 OR FIND_IN_SET(?, lf.loc_genre) > 0";
//	public static final String SELECT_FILMS_BY_COUNTRY = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id WHERE FIND_IN_SET(?, f.f_country) > 0 OR FIND_IN_SET(?, lf.loc_country) > 0";
//	public static final String SELECT_FILMS_BETWEEN_YEARS = "SELECT f.f_id, COALESCE(lf.loc_name, f.f_name) AS fname, f.f_year as year, COALESCE(lf.loc_direct, f.f_direct) AS director, COALESCE(lf.loc_country, f.f_country) AS country, COALESCE(lf.loc_genre, f.f_genre) AS genre, COALESCE(lf.loc_actors, f.f_actors) AS actors, COALESCE(lf.loc_composer, f.f_composer) AS composer, COALESCE(lf.loc_description, f.f_description) AS description, f.f_length, f.f_rating AS rating, f.f_price FROM films AS f LEFT JOIN (SELECT * FROM films_local WHERE loc_lang = ?) AS lf ON f.f_id = lf.loc_id WHERE f.f_year >= ? AND f.f_year <= ?";
//
//	public static final String SHOW_ALL_GENRES_EN = "SHOW COLUMNS FROM films LIKE 'f_genre'";
//	public static final String SHOW_ALL_COUNTRIES_EN = "SHOW COLUMNS FROM films LIKE 'f_country'";
//
//	public static final String SHOW_ALL_GENRES_RU = "SHOW COLUMNS FROM films_local LIKE 'loc_genre'";
//	public static final String SHOW_ALL_COUNTRIES_RU = "SHOW COLUMNS FROM films_local LIKE 'loc_country'";

//	@Override
//	public int create(Film film) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		PreparedStatement st2 = null;
//		ResultSet rs = null;
//
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(INSERT_NEW_FILM);
//			st.setString(1, film.getName());
//			st.setInt(2, film.getYear());
//			st.setString(3, film.getDirector());
//
//			if (film.getCountry() == null){
//				st.setNull(4, Types.VARCHAR);
//			}
//			else {
//				st.setString(4, film.getCountry());
//			}
//
//			if (film.getGenre() == null){
//				st.setNull(5, Types.VARCHAR);
//			}
//			else {
//				st.setString(5, film.getGenre());
//			}
//
//			if (film.getActors() == null){
//				st.setNull(6, Types.VARCHAR);
//			}
//			else {
//				st.setString(6, film.getActors());
//			}
//
//			if (film.getComposer() == null){
//				st.setNull(7, Types.VARCHAR);
//			}
//			else {
//				st.setString(7, film.getComposer());
//			}
//
//			if (film.getDescription() == null){
//				st.setNull(8, Types.VARCHAR);
//			}
//			else {
//				st.setString(8, film.getDescription());
//			}
//
//			st.setInt(9, film.getLength());
//			st.setFloat(10, film.getPrice());
//			st.executeUpdate();
//
//			st2 = con.prepareStatement(SELECT_NEW_FILM_ID);
//			st2.setString(1, film.getName());
//			st2.setInt(2, film.getYear());
//			st2.setString(3, film.getDirector());
//			st2.setInt(4, film.getLength());
//			rs = st2.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_INSERT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (st != null) {
//					st.close();
//				}
//				if (st2 != null) {
//					st2.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//
//		return 0;
//
//	}
//
//    /**
//     * Deletes a film from the data source
//     *
//     * @param id ID of a film which will be deleted
//     * @throws DAOException
//     */
//	@Override
//	public void delete(int id) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(DELETE_FILM);
//			st.setInt(1, id);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_DELETE_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void update(int id, Film editedFilm) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(UPDATE_FILM_BY_ID);
//			st.setString(1, editedFilm.getName());
//			st.setInt(2, editedFilm.getYear());
//			st.setString(3, editedFilm.getDirector());
//
//			if (editedFilm.getCountry() == null) {
//				st.setNull(4, Types.VARCHAR);
//			}
//			else {
//				st.setString(4, editedFilm.getCountry());
//			}
//
//			if (editedFilm.getGenre() == null){
//				st.setNull(5, Types.VARCHAR);
//			}
//			else {
//				st.setString(5, editedFilm.getGenre());
//			}
//
//			if (editedFilm.getActors() == null){
//				st.setNull(6, Types.VARCHAR);
//			}
//			else {
//				st.setString(6, editedFilm.getActors());
//			}
//
//			if (editedFilm.getComposer() == null){
//				st.setNull(7, Types.VARCHAR);
//			}
//			else {
//				st.setString(7, editedFilm.getComposer());
//			}
//
//			if (editedFilm.getDescription() == null){
//				st.setNull(8, Types.VARCHAR);
//			}
//			else {
//				st.setString(8, editedFilm.getDescription());
//			}
//
//			st.setInt(9, editedFilm.getLength());
//			st.setFloat(10, editedFilm.getPrice());
//			st.setInt(11, id);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_UPDATE_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//	}
//
//	@Override
//	public List<Film> getAll(String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ALL_FILMS_ORDERED_BY_RATING);
//			st.setString(1, lang);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public Film getById(int id, String lang) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILM_BY_ID);
//			st.setString(1, lang);
//			st.setInt(2, id);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				return film;
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public String getFilmNameByID(int id, String lang) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILM_NAME_BY_ID);
//			st.setString(1, lang);
//			st.setInt(2, id);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				return rs.getString(1);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public List<Film> getFilmsByName(String name, String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILMS_BY_NAME);
//			st.setString(1, lang);
//			st.setString(2, name);
//			st.setString(3, name);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//
//	@Override
//	public List<Film> getFilmsByYear(int year, String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILMS_BY_YEAR);
//			st.setString(1, lang);
//			st.setInt(2, year);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public List<Film> getFilmsByGenre(String genre, String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILMS_BY_GENRE);
//			st.setString(1, lang);
//			st.setString(2, genre);
//			st.setString(3, genre);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public List<Film> getFilmsByCountry(String country, String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILMS_BY_COUNTRY);
//			st.setString(1, lang);
//			st.setString(2, country);
//			st.setString(3, country);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public List<Film> getTwelveLastAddedFilms(String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_TWELVE_LAST_ADDED_FILMS);
//			st.setString(1, lang);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public List<Film> getFilmsBetweenYears(int yearFrom, int yearTo, String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_FILMS_BETWEEN_YEARS);
//			st.setString(1, lang);
//			st.setInt(2, yearFrom);
//			st.setInt(3, yearTo);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public String[] getAvailableGenres(String lang) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			switch (lang) {
//				case "ru":
//					st = con.prepareStatement(SHOW_ALL_GENRES_RU);
//					break;
//				default:
//					st = con.prepareStatement(SHOW_ALL_GENRES_EN);
//					break;
//			}
//
//			rs = st.executeQuery();
//			if (rs.next()) {
//				String s = rs.getString(2);
//				String ss = s.substring(5, s.length() - 2);
//				String[] sA = ss.split("','");
//				return sA;
//			}
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SHOW_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public String[] getAvailableCountries(String lang) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			switch (lang) {
//			case "ru":
//				st = con.prepareStatement(SHOW_ALL_COUNTRIES_RU);
//				break;
//			default:
//				st = con.prepareStatement(SHOW_ALL_COUNTRIES_EN);
//			}
//			rs = st.executeQuery();
//			if (rs.next()) {
//				String s = rs.getString(2);
//				String ss = s.substring(5, s.length() - 2);
//				String[] sA = ss.split("','");
//				return sA;
//			}
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SHOW_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public List<Film> getAllPart(int start, int amount, String lang) throws DAOException {
//		List<Film> filmSet = new ArrayList<Film>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ALL_FILMS_PART_ORDERED_BY_RATING);
//			st.setString(1, lang);
//			st.setInt(2, start);
//			st.setInt(3, amount);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Film film = new Film();
//				film.setId(rs.getInt(1));
//				film.setName(rs.getString(2));
//				film.setYear(rs.getInt(3));
//				film.setDirector(rs.getString(4));
//				film.setCountry(rs.getString(5));
//				film.setGenre(rs.getString(6));
//				film.setActors(rs.getString(7));
//				film.setComposer(rs.getString(8));
//				film.setDescription(rs.getString(9));
//				film.setLength(rs.getInt(10));
//				film.setRating(rs.getFloat(11));
//				film.setPrice(rs.getFloat(12));
//
//				filmSet.add(film);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return filmSet;
//	}
//
//	@Override
//	public int getNumberOfFilms() throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ALL_FILMS_COUNT);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) {
//					pool.closeConnection(con);
//				}
//			}
//		}
//		return 0;
//	}
}
