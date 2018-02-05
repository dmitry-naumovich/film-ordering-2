package by.epam.naumovich.film_ordering.dao.impl;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.dao.IUserDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.dao.pool.ConnectionPool;
import by.epam.naumovich.film_ordering.dao.pool.exception.ConnectionPoolException;
import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IUserDAO interface implementation that works with MySQL database
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Repository
public class MySQLUserDAO /*implements IUserDAO*/ {

//	public static final String INSERT_NEW_USER = "INSERT INTO users (u_login, u_name, u_surname, u_passw, u_sex, u_type, u_regdate, u_regtime, u_bdate, u_phone, u_email, u_about) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//	public static final String SELECT_NEW_USER_ID_BY_LOGIN = "SELECT u_id FROM users WHERE u_login = ?";
//	public static final String UPDATE_USER_BY_ID = "UPDATE users SET u_name = ?, u_surname = ?, u_passw = ?, u_sex = ?, u_bdate = ?, u_phone = ?, u_email = ?, u_about = ? WHERE u_id = ?";
//	public static final String DELETE_USER = "DELETE FROM users WHERE u_id = ?";
//	public static final String SELECT_ALL_USERS = "SELECT * FROM users ORDER BY u_regdate DESC, u_regtime DESC";
//	public static final String SELECT_ALL_USERS_PART = "SELECT * FROM users ORDER BY u_regdate DESC, u_regtime DESC LIMIT ?, ?";
//	public static final String SELECT_ALL_USERS_COUNT = "SELECT COUNT(*) FROM users";
//	public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE u_login = ?";
//	public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE u_id = ?";
//	public static final String SELECT_USERS_IN_BAN = "SELECT users.* FROM users JOIN bans ON users.u_id = bans.b_user WHERE b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY))) ORDER BY bans.b_stdate DESC, bans.b_sttime DESC";
//	public static final String SELECT_USER_IN_BAN_NOW_BY_ID = "SELECT * FROM bans WHERE bans.b_user = ? AND b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY)))";
//	public static final String SELECT_PASSWORD_BY_LOGIN = "SELECT u_passw FROM users WHERE u_login = ?";
//	public static final String SELECT_CURRENT_DISCOUNT_BY_USER_ID = "SELECT * FROM discounts WHERE d_user = ? AND ((CURDATE() = d_stdate AND CURTIME() > d_sttime) OR (CURDATE() = d_endate AND CURTIME() < d_entime) OR (CURDATE() > d_stdate AND CURDATE() < d_endate))";
//	public static final String INSERT_NEW_DISCOUNT = "INSERT INTO discounts (d_user, d_amount, d_stdate, d_sttime, d_endate, d_entime) VALUES (?, ?, ?, ?, ?, ?)";
//	public static final String UPDATE_DISCOUNT = "UPDATE discounts SET d_amount = ?, d_endate = ?, d_entime = ? WHERE d_id = ?";
//	public static final String DELETE_DISCOUNT = "DELETE FROM discounts WHERE d_id = ?";
//	public static final String SELECT_NEW_DISCOUNT_ID = "SELECT d_id FROM discounts WHERE d_user = ? AND d_amount = ? AND d_stdate = ? AND d_sttime = ?";
//	public static final String SELECT_DISCOUNT_BY_ID = "SELECT * FROM discounts WHERE d_id = ?";
	
//	public static final String INSERT_BAN_RECORD = "INSERT INTO bans (b_user, b_stdate, b_sttime, b_length, b_reason) VALUES (?, ?, ?, ?, ?)";
//	public static final String UNBAN_USER_BY_ID = "UPDATE bans SET b_active = 0 WHERE b_user = ?";
	
//	public static final String SELECT_CURRENT_BAN_REASON_BY_ID = "SELECT b_reason FROM bans WHERE b_user = ? AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY))) AND b_active = 1";
//	public static final String SELECT_CURRENT_BAN_END_BY_ID = "SELECT DATE_ADD(b_stdate, INTERVAL b_length DAY), b_sttime FROM bans WHERE b_user = ?  AND b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY)))";

//	@Override
//	public User save(User user) throws DAOException {
//		Object[] params = new Object[] {user.getLogin(), user.getName(), user.getSurname(), user.getPassword(), String.valueOf(user.getSex()),
//				String.valueOf(user.getType()), user.getRegDate(), user.getRegTime(), user.getBirthDate(), user.getPhone(), user.getEmail(), user.getAbout()};
//
//		int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.TIME, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
//
//		jdbcTemplate.save(INSERT_NEW_USER, params, types);
//		return null;

//		Object[] params = new Object[]{login};
//		List<Integer> ints = jdbcTemplate.query(SELECT_ID_BY_LOGIN, params, new IntegerRowMapper());
//		return ints.get(0);
//	}

//	@Override
//	public void save(int id, User user) throws DAOException {
//		Object[] params = new Object[] {user.getLogin(), user.getName(), user.getSurname(), user.getPassword(), String.valueOf(user.getSex()),
//				String.valueOf(user.getType()), user.getRegDate(), user.getRegTime(), user.getBirthDate(), user.getPhone(), user.getEmail(), user.getAbout(), id};
//
//		int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.TIME, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
//
//		jdbcTemplate.update(UPDATE_USER_BY_ID, params, types);
//	}
//
//	@Override
//	public void delete(int id) throws DAOException {
//		Object[] params = new Object[] {id};
//		int[] types = new int[] {Types.INTEGER};
//		jdbcTemplate.update(DELETE_USER, params, types);
//	}
	
//	@Override
//	//TODO: check it
//	public List<User> findAllByOrderByDateDescTimeDesc() throws DAOException {
//		return new ArrayList<>(jdbcTemplate.query(SELECT_ALL_USERS, new UserRowMapper()));
//	}
	
//	@Override
//	public User findByLogin(String login) throws DAOException {
//		Object[] params = new Object[] {login};
//		List<User> users = jdbcTemplate.query(SELECT_USER_BY_LOGIN, params, new UserRowMapper());
//		return users.get(0);
//	}
//
//	@Override
//	public List<User> findBanned() throws DAOException {
//		return new ArrayList<>(jdbcTemplate.query(SELECT_USERS_IN_BAN, new UserRowMapper()));
//	}
//
//	@Override
//	public boolean userIsInBan(int id) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_USER_IN_BAN_NOW_BY_ID);
//			st.setInt(1, id);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				return true;
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
//		return false;
//	}
//
//	@Override
//	public String getPasswordByLogin(String login) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_PASSWORD_BY_LOGIN);
//			st.setString(1, login);
//			rs = st.executeQuery();
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
//	public User findOne(int id) throws DAOException {
//		Object[] params = new Object[] {id};
//		List<User> users = jdbcTemplate.query(SELECT_USER_BY_ID, params, new UserRowMapper());
//		return users.get(0);
//	}
//
//	@Override
//	public Discount findDiscountByUserId(int id) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_CURRENT_DISCOUNT_BY_USER_ID);
//			st.setInt(1, id);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				Discount discount = new Discount();
//				discount.setId(rs.getInt(1));
//				discount.setUserID(rs.getInt(2));
//				discount.setAmount(rs.getInt(3));
//				discount.setStDate(rs.getDate(4));
//				discount.setStTime(rs.getTime(5));
//				discount.setEnDate(rs.getDate(6));
//				discount.setEnTime(rs.getTime(7));
//				return discount;
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
//	public void banUser(int userID, Date startDate, Time startTime, int length, String reason) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(INSERT_BAN_RECORD);
//			st.setInt(1, userID);
//			st.setDate(2, startDate);
//			st.setTime(3, startTime);
//			st.setInt(4, length);
//			st.setString(5, reason);
//			st.executeUpdate();
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
//	}
//
//	@Override
//	public void unbanUser(int userID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(UNBAN_USER_BY_ID);
//			st.setInt(1, userID);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_UPDATE_FAILURE, e);
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
//	public String getCurrentBanEnd(int userID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_CURRENT_BAN_END_BY_ID);
//			st.setInt(1, userID);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				return rs.getString(1) + " " + rs.getString(2);
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
//	public String getCurrentBanReason(int userID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_CURRENT_BAN_REASON_BY_ID);
//			st.setInt(1, userID);
//			rs = st.executeQuery();
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
//	public int save(Discount discount) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		PreparedStatement st2 = null;
//		ResultSet rs = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(INSERT_NEW_DISCOUNT);
//			st.setInt(1, discount.getUserID());
//			st.setInt(2, discount.getAmount());
//			st.setDate(3, discount.getStDate());
//			st.setTime(4, discount.getStTime());
//			st.setDate(5, discount.getEnDate());
//			st.setTime(6, discount.getEnTime());
//			st.executeUpdate();
//
//			st2 = con.prepareStatement(SELECT_NEW_DISCOUNT_ID);
//			st2.setInt(1, discount.getUserID());
//			st2.setInt(2, discount.getAmount());
//			st2.setDate(3, discount.getStDate());
//			st2.setTime(4, discount.getStTime());
//			rs = st2.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_INSERT_FAILURE, e);
//		} catch (ConnectionPoolException e) {
//			throw new DAOException(ExceptionMessages.CONNECTION_NOT_TAKEN, e);
//		} finally {
//			try {
//				if (st != null) { st.close(); }
//				if (st2 != null) { st2.close(); }
//				if (rs != null) { rs.close(); }
//			} catch (SQLException e) {
//				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
//			} finally {
//				if (con != null) { pool.closeConnection(con); }
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public void updateDiscount(Discount editedDisc) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(UPDATE_DISCOUNT);
//			st.setInt(1, editedDisc.getAmount());
//			st.setDate(2, editedDisc.getEnDate());
//			st.setTime(3, editedDisc.getEnTime());
//			st.setInt(4, editedDisc.getId());
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DAOException(ExceptionMessages.SQL_UPDATE_FAILURE, e);
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
//	public void delete(int discountID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(DELETE_DISCOUNT);
//			st.setInt(1, discountID);
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
//	public Discount getDiscountByID(int discountID) throws DAOException {
//		ConnectionPool pool = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//
//			pool = ConnectionPool.getInstance();
//			con = pool.getConnection();
//			st = con.prepareStatement(SELECT_DISCOUNT_BY_ID);
//
//			st.setInt(1, discountID);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				Discount discount = new Discount();
//				discount.setId(rs.getInt(1));
//				discount.setUserID(rs.getInt(2));
//				discount.setAmount(rs.getInt(3));
//				discount.setStDate(rs.getDate(4));
//				discount.setStTime(rs.getTime(5));
//				discount.setEnDate(rs.getDate(6));
//				discount.setEnTime(rs.getTime(7));
//				return discount;
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
//	public List<User> findAllPart(int start, int amount) throws DAOException {
//		Object[] params = new Object[] {start, amount};
//		return new ArrayList<>(jdbcTemplate.query(SELECT_ALL_USERS_PART, params, new UserRowMapper()));
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
//			st = con.prepareStatement(SELECT_ALL_USERS_COUNT);
//
//			rs = st.executeQuery();
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
