package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.dao.IOrderDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.dao.pool.ConnectionPool;
import by.epam.naumovich.film_ordering.dao.pool.exception.ConnectionPoolException;

/**
 * IOrderDAO interface implementation that works with MySQL database
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class MySQLOrderDAO /*implements IOrderDAO*/ {

//	public static final String INSERT_NEW_ORDER = "INSERT INTO orders (o_user, o_film, o_date, o_time, o_fprice, o_discount, o_paym) VALUES (?, ?, ?, ?, ?, ?, ?)";
//	public static final String DELETE_ORDER = "DELETE FROM orders WHERE o_num = ?";
	
//	public static final String SELECT_ALL_ORDERS = "SELECT * FROM orders ORDER BY o_date DESC, o_time DESC";
//	public static final String SELECT_ALL_ORDERS_PART = "SELECT * FROM orders ORDER BY o_date DESC, o_time DESC LIMIT ?, ?";
//	public static final String SELECT_ALL_ORDERS_COUNT = "SELECT COUNT(*) FROM orders";
	
//	public static final String SELECT_ORDER_BY_ORDER_NUM = "SELECT * FROM orders WHERE o_num = ?";
//	public static final String SELECT_ORDER_BY_USER_AND_FILM_ID = "SELECT * FROM orders WHERE o_user = ? AND o_film = ?";
//	public static final String SELECT_ORDER_NUM_BY_USER_AND_FILM_ID = "SELECT o_num FROM orders WHERE o_user = ? AND o_film = ?";
	
//	public static final String SELECT_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE o_user = ? ORDER BY o_date DESC, o_time DESC";
//	public static final String SELECT_ORDERS_PART_BY_USER_ID = "SELECT * FROM orders WHERE o_user = ? ORDER BY o_date DESC, o_time DESC LIMIT ?, ?";
//	public static final String SELECT_ORDERS_COUNT_BY_USER_ID = "SELECT COUNT(*) FROM orders WHERE o_user = ?";
	
//	public static final String SELECT_ORDERS_BY_FILM_ID = "SELECT * FROM orders WHERE o_film = ? ORDER BY o_date DESC, o_time DESC";
//	public static final String SELECT_ORDERS_PART_BY_FILM_ID = "SELECT * FROM orders WHERE o_film = ? ORDER BY o_date DESC, o_time DESC LIMIT ?, ?";
//	public static final String SELECT_ORDERS_COUNT_BY_FILM_ID = "SELECT COUNT(*) FROM orders WHERE o_film = ?";
	
//	/**
//	 * Singleton MySQLOrderDAO instance
//	 */
//	private static final MySQLOrderDAO instance = new MySQLOrderDAO();
//
//	/**
//	 * Static method that returns singleton MySQLOrderDAO instance
//	 * @return MySQLOrderDAO object
//	 */
//	public static MySQLOrderDAO getInstance() {
//		return instance;
//	}
//
//	@Override
//	public int save(Order order) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		PreparedStatement st2 = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(INSERT_NEW_ORDER);
//			st.setInt(1, order.getUserId());
//			st.setInt(2, order.getFilmId());
//			st.setDate(3, order.getDate());
//			st.setTime(4, order.getTime());
//			st.setFloat(5, order.getPrice());
//			st.setInt(6, order.getDiscount());
//			st.setFloat(7, order.getPayment());
//			st.executeUpdate();
//
//			st2 = con.prepareStatement(SELECT_ORDER_NUM_BY_USER_AND_FILM_ID);
//			st2.setInt(1, order.getUserId());
//			st2.setInt(2, order.getFilmId());
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
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//
//		return 0;
//	}
//
//	@Override
//	public void delete(int orderNum) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(DELETE_ORDER);
//			st.setInt(1, orderNum);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_DELETE_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.PREP_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//	}
//
//	@Override
//	public Order findOne(int orderNum) throws DAOException {
//
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDER_BY_ORDER_NUM);
//			st.setInt(1, orderNum);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				return order;
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Order findByUserIdAndFilmId(int userID, int filmID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDER_BY_USER_AND_FILM_ID);
//			st.setInt(1, userID);
//			st.setInt(2, filmID);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//				return order;
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_INSERT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public List<Order> findByUserIdOrderByDateDescTimeDesc(int id) throws DAOException {
//		List<Order> orderSet = new ArrayList<Order>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDERS_BY_USER_ID);
//			st.setString(1, String.valueOf(id));
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				orderSet.add(order);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_INSERT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return orderSet;
//	}
//
//	@Override
//	public List<Order> findByFilmIdOrderByDateDescTimeDesc(int id) throws DAOException {
//		List<Order> orderSet = new ArrayList<Order>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDERS_BY_FILM_ID);
//			st.setString(1, String.valueOf(id));
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				orderSet.add(order);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return orderSet;
//	}
//
//	@Override
//	public List<Order> findAllByOrderByDateDescTimeDesc() throws DAOException {
//		List<Order> orderSet = new ArrayList<Order>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ALL_ORDERS);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				orderSet.add(order);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return orderSet;
//	}
//
//	@Override
//	public List<Order> findAllPart(int start, int amount) throws DAOException {
//		List<Order> orderSet = new ArrayList<Order>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ALL_ORDERS_PART);
//			st.setInt(1, start);
//			st.setInt(2, amount);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				orderSet.add(order);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return orderSet;
//	}
//
//	@Override
//	public int count() throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ALL_ORDERS_COUNT);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public List<Order> findPartByUserId(int id, int start, int amount) throws DAOException {
//		List<Order> orderSet = new ArrayList<Order>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDERS_PART_BY_USER_ID);
//			st.setInt(1, id);
//			st.setInt(2, start);
//			st.setInt(3, amount);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				orderSet.add(order);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return orderSet;
//	}
//
//	@Override
//	public List<Order> findPartByFilmId(int id, int start, int amount) throws DAOException {
//		List<Order> orderSet = new ArrayList<Order>();
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDERS_PART_BY_FILM_ID);
//			st.setInt(1, id);
//			st.setInt(2, start);
//			st.setInt(3, amount);
//			rs = st.executeQuery();
//
//			while (rs.next()) {
//				Order order = new Order();
//				order.setOrdNum(rs.getInt(1));
//				order.setUserId(rs.getInt(2));
//				order.setFilmId(rs.getInt(3));
//				order.setDate(rs.getDate(4));
//				order.setTime(rs.getTime(5));
//				order.setPrice(rs.getFloat(6));
//				order.setDiscount(rs.getInt(7));
//				order.setPayment(rs.getFloat(8));
//
//				orderSet.add(order);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return orderSet;
//	}
//
//	@Override
//	public int countByUserId(int userID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDERS_COUNT_BY_USER_ID);
//			st.setInt(1, userID);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public int countByFilmId(int filmID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_ORDERS_COUNT_BY_FILM_ID);
//			st.setInt(1, filmID);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_SELECT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (rs != null) { rs.close(); }
//				if (st != null) { st.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return 0;
//	}
}