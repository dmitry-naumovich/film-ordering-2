package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.dao.INewsDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.dao.pool.ConnectionPool;
import by.epam.naumovich.film_ordering.dao.pool.exception.ConnectionPoolException;

/**
 * INewsDAO interface implementation that works with MySQL database
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class MySQLNewsDAO implements INewsDAO {

	public static final String INSERT_NEW_NEWS = "INSERT INTO news (n_date, n_time, n_title, n_text) VALUES (?, ?, ?, ?)";
	public static final String DELETE_NEWS = "DELETE FROM news WHERE n_id = ?";
	public static final String UPDATE_NEWS = "UPDATE news SET n_title = ?, n_text = ? WHERE n_id = ?";
	public static final String SELECT_ALL_NEWS = "SELECT * FROM news ORDER BY n_date DESC, n_time DESC";
	public static final String SELECT_ALL_NEWS_PART = "SELECT * FROM news ORDER BY n_date DESC, n_time DESC LIMIT ?, ?";
	public static final String SELECT_ALL_NEWS_COUNT = "SELECT COUNT(*) FROM news";
	public static final String SELECT_NEWS_BY_ID = "SELECT * FROM news WHERE n_id = ?";
	public static final String SELECT_NEWS_BY_YEAR = "SELECT * FROM news WHERE YEAR(n_date) = ? ORDER BY n_date DESC, n_time DESC";
	public static final String SELECT_NEWS_BY_MONTH_AND_YEAR = "SELECT * FROM news WHERE MONTH(n_date) = ? AND YEAR(n_date) = ? ORDER BY n_date DESC, n_time DESC";
	public static final String SELECT_NEW_NEWS_ID = "SELECT n_id FROM news WHERE n_title = ? AND n_text = ?";
	
	/**
	 * Singleton MySQLNewsDAO instance
	 */
	private static final MySQLNewsDAO instance = new MySQLNewsDAO();
	
	/**
	 * Static method that returns singleton MySQLNewsDAO instance
	 * @return MySQLNewsdDAO object
	 */
	public static MySQLNewsDAO getInstance() {
		return instance;
	}
	
	@Override
	public int addNews(News news) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(INSERT_NEW_NEWS);
			st.setDate(1, news.getDate());
			st.setTime(2, news.getTime());
			st.setString(3, news.getTitle());
			st.setString(4, news.getText());
			st.executeUpdate();
			
			st2 = con.prepareStatement(SELECT_NEW_NEWS_ID);
			
			st2.setString(1, news.getTitle());
			st2.setString(2, news.getText());
			rs = st2.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_INSERT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (st != null) { st.close(); }
				if (rs != null) { rs.close(); }
				if (st2 != null) { st2.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
		}
		return 0;
	}

	@Override
	public void deleteNews(int id) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(DELETE_NEWS);
			st.setInt(1, id);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_DELETE_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
		}
	}
	
	@Override
	public void editNews(int id, News news) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(UPDATE_NEWS);
			st.setString(1, news.getTitle());
			st.setString(2, news.getText());
			st.setInt(3, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_UPDATE_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
		}
	}

	@Override
	public Set<News> getAllNews() throws DAOException {
		Set<News> newsSet = new LinkedHashSet<News>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_ALL_NEWS);
			rs = st.executeQuery();
			
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setDate(rs.getDate(2));
				news.setTime(rs.getTime(3));
				news.setTitle(rs.getString(4));
				news.setText(rs.getString(5));
				newsSet.add(news);
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
			
		}
		return newsSet;
	}

	@Override
	public Set<News> getNewsByYear(int year) throws DAOException {
		Set<News> newsSet = new LinkedHashSet<News>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_NEWS_BY_YEAR);
			st.setInt(1, year);
			rs = st.executeQuery();
			
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setDate(rs.getDate(2));
				news.setTime(rs.getTime(3));
				news.setTitle(rs.getString(4));
				news.setText(rs.getString(5));
				
				newsSet.add(news);
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
			
		}
		return newsSet;
	}

	@Override
	public Set<News> getNewsByMonthAndYear(int month, int year) throws DAOException {
		Set<News> newsSet = new LinkedHashSet<News>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_NEWS_BY_MONTH_AND_YEAR);
			st.setInt(1, month);
			st.setInt(2, year);
			rs = st.executeQuery();
			
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setDate(rs.getDate(2));
				news.setTime(rs.getTime(3));
				news.setTitle(rs.getString(4));
				news.setText(rs.getString(5));
				
				newsSet.add(news);
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
			
		}
		return newsSet;
	}

	@Override
	public News getNewsById(int id) throws DAOException {
		News news = null;
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_NEWS_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			while (rs.next()) {
				news = new News();
				news.setId(rs.getInt(1));
				news.setDate(rs.getDate(2));
				news.setTime(rs.getTime(3));
				news.setTitle(rs.getString(4));
				news.setText(rs.getString(5));
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
			
		}
		return news;
	}

	@Override
	public Set<News> getAllNewsPart(int start, int amount) throws DAOException {
		Set<News> newsSet = new LinkedHashSet<News>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_ALL_NEWS_PART);
			st.setInt(1, start);
			st.setInt(2, amount);
			rs = st.executeQuery();
			
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setDate(rs.getDate(2));
				news.setTime(rs.getTime(3));
				news.setTitle(rs.getString(4));
				news.setText(rs.getString(5));
				newsSet.add(news);
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
			
		}
		return newsSet;
	}

	@Override
	public int getNumberOfNews() throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_ALL_NEWS_COUNT);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
		} catch (ConnectionPoolException e) {
			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (st != null) { st.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
			
		}
		return 0;
	}
}
