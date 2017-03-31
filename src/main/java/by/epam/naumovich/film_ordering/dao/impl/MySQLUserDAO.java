package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.LinkedHashSet;
import java.util.Set;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.dao.IUserDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.dao.pool.ConnectionPool;
import by.epam.naumovich.film_ordering.dao.pool.exception.ConnectionPoolException;

/**
 * IUserDAO interface implementation that works with MySQL database
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class MySQLUserDAO implements IUserDAO {

	public static final String INSERT_NEW_USER = "INSERT INTO users (u_login, u_name, u_surname, u_passw, u_sex, u_type, u_regdate, u_regtime, u_bdate, u_phone, u_email, u_about) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SELECT_NEW_USER_ID_BY_LOGIN = "SELECT u_id FROM users WHERE u_login = ?";
	public static final String UPDATE_USER_BY_ID = "UPDATE users SET u_name = ?, u_surname = ?, u_passw = ?, u_sex = ?, u_bdate = ?, u_phone = ?, u_email = ?, u_about = ? WHERE u_id = ?";	
	public static final String DELETE_USER = "DELETE FROM users WHERE u_id = ?";
	public static final String SELECT_ALL_USERS = "SELECT * FROM users ORDER BY u_regdate DESC, u_regtime DESC";
	public static final String SELECT_ALL_USERS_PART = "SELECT * FROM users ORDER BY u_regdate DESC, u_regtime DESC LIMIT ?, ?";
	public static final String SELECT_ALL_USERS_COUNT = "SELECT COUNT(*) FROM users";
	public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE u_login = ?";
	public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE u_id = ?";
	public static final String SELECT_USERS_IN_BAN = "SELECT users.* FROM users JOIN bans ON users.u_id = bans.b_user WHERE b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY))) ORDER BY bans.b_stdate DESC, bans.b_sttime DESC";
	public static final String SELECT_USER_IN_BAN_NOW_BY_ID = "SELECT * FROM bans WHERE bans.b_user = ? AND b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY)))";
	public static final String SELECT_PASSWORD_BY_LOGIN = "SELECT u_passw FROM users WHERE u_login = ?";
	public static final String SELECT_CURRENT_DISCOUNT_BY_USER_ID = "SELECT * FROM discounts WHERE d_user = ? AND ((CURDATE() = d_stdate AND CURTIME() > d_sttime) OR (CURDATE() = d_endate AND CURTIME() < d_entime) OR (CURDATE() > d_stdate AND CURDATE() < d_endate))";
	public static final String INSERT_NEW_DISCOUNT = "INSERT INTO discounts (d_user, d_amount, d_stdate, d_sttime, d_endate, d_entime) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_DISCOUNT = "UPDATE discounts SET d_amount = ?, d_endate = ?, d_entime = ? WHERE d_id = ?";
	public static final String DELETE_DISCOUNT = "DELETE FROM discounts WHERE d_id = ?";
	public static final String SELECT_NEW_DISCOUNT_ID = "SELECT d_id FROM discounts WHERE d_user = ? AND d_amount = ? AND d_stdate = ? AND d_sttime = ?";
	public static final String SELECT_DISCOUNT_BY_ID = "SELECT * FROM discounts WHERE d_id = ?";
	
	public static final String INSERT_BAN_RECORD = "INSERT INTO bans (b_user, b_stdate, b_sttime, b_length, b_reason) VALUES (?, ?, ?, ?, ?)";
	public static final String UNBAN_USER_BY_ID = "UPDATE bans SET b_active = 0 WHERE b_user = ?";
	
	public static final String SELECT_CURRENT_BAN_REASON_BY_ID = "SELECT b_reason FROM bans WHERE b_user = ? AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY))) AND b_active = 1";
	public static final String SELECT_CURRENT_BAN_END_BY_ID = "SELECT DATE_ADD(b_stdate, INTERVAL b_length DAY), b_sttime FROM bans WHERE b_user = ?  AND b_active = 1 AND ((CURDATE() = b_stdate AND CURTIME() > b_sttime) OR (CURDATE() = DATE_ADD(b_stdate, INTERVAL b_length DAY) AND CURTIME() < b_sttime) OR (CURDATE() > b_stdate AND CURDATE() < DATE_ADD(b_stdate, INTERVAL b_length DAY)))";
	
	/**
	 * Singleton MySQLUserDAO instance
	 */
	private static final MySQLUserDAO instance = new MySQLUserDAO();
	
	/**
	 * Static method that returns singleton MySQLUserDAO instance
	 * @return MySQLUserDAO object
	 */
	public static MySQLUserDAO getInstance() {
		return instance; 
	}
	
	@Override
	public int addUser(User user) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;		
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(INSERT_NEW_USER);
			st.setString(1, user.getLogin());
			st.setString(2, user.getName());
			st.setString(3, user.getSurname());
			st.setString(4, user.getPassword());
			st.setString(5, String.valueOf(user.getSex()));
			st.setString(6, String.valueOf(user.getType()));
			st.setDate(7, user.getRegDate());
			st.setTime(8, user.getRegTime());
			if (user.getBirthDate() == null){
				st.setNull(9, Types.DATE);
			}
			else {
				st.setDate(9, user.getBirthDate());
			}
			if (user.getPhone() == null){
				st.setNull(10, Types.CHAR);
			}
			else {
				st.setString(10, user.getPhone());
			}
			
			st.setString(11, user.getEmail());
			
			if (user.getAbout() == null){
				st.setNull(12, Types.VARCHAR);
			}
			else {
				st.setString(12, user.getAbout());
			}
			st.executeUpdate();
			
			st2 = con.prepareStatement(SELECT_NEW_USER_ID_BY_LOGIN);
			st2.setString(1, user.getLogin());
			ResultSet rs = st2.executeQuery();
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
	public void updateUser(int id, User updatedUser) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(UPDATE_USER_BY_ID);
			st.setString(1, updatedUser.getName());
			st.setString(2, updatedUser.getSurname());
			st.setString(3, updatedUser.getPassword());
			st.setString(4, String.valueOf(updatedUser.getSex()));
			if (updatedUser.getBirthDate() == null){
				st.setNull(5, Types.DATE);
			}
			else {
				st.setDate(5, updatedUser.getBirthDate());
			}

			if (updatedUser.getPhone() == null){
				st.setNull(6, Types.VARCHAR);
			}
			else {
				st.setString(6, updatedUser.getPhone());
			}
			
			st.setString(7, updatedUser.getEmail());
			
			
			if (updatedUser.getAbout() == null){
				st.setNull(8, Types.VARCHAR);
			}
			else {
				st.setString(8, updatedUser.getAbout());
			}
			st.setInt(9, id);
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
	public void deleteUser(int id) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(DELETE_USER);
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
	public Set<User> getAllUsers() throws DAOException {
		Set<User> userSet = new LinkedHashSet<User>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_ALL_USERS);
			rs = st.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurname(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setSex(rs.getString(6).charAt(0));
				user.setType(rs.getString(7).charAt(0));
				user.setRegDate(rs.getDate(8));
				user.setRegTime(rs.getTime(9));
				user.setBirthDate(rs.getDate(10));
				user.setPhone(rs.getString(11));
				user.setEmail(rs.getString(12));
				user.setAbout(rs.getString(13));
				
				userSet.add(user);
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
		return userSet;
	}
	
	@Override
	public User getUserByLogin(String login) throws DAOException {
		User user = null;
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_USER_BY_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurname(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setSex(rs.getString(6).charAt(0));
				user.setType(rs.getString(7).charAt(0));
				user.setRegDate(rs.getDate(8));
				user.setRegTime(rs.getTime(9));
				user.setBirthDate(rs.getDate(10));
				user.setPhone(rs.getString(11));
				user.setEmail(rs.getString(12));
				user.setAbout(rs.getString(13));
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
		return user;
	}

	@Override
	public Set<User> getUsersInBan() throws DAOException {
		Set<User> userSet = new LinkedHashSet<User>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_USERS_IN_BAN);
			rs = st.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurname(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setSex(rs.getString(6).charAt(0));
				user.setType(rs.getString(7).charAt(0));
				user.setRegDate(rs.getDate(8));
				user.setRegTime(rs.getTime(9));
				user.setBirthDate(rs.getDate(10));
				user.setPhone(rs.getString(11));
				user.setEmail(rs.getString(12));
				user.setAbout(rs.getString(13));
				
				userSet.add(user);
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
		return userSet;
	}

	@Override
	public boolean userIsInBan(int id) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_USER_IN_BAN_NOW_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return true;
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
		return false;
	}

	@Override
	public String getPasswordByLogin(String login) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_PASSWORD_BY_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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
		return null;
	}

	@Override
	public User getUserByID(int id) throws DAOException {
		User user = null;
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_USER_BY_ID);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurname(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setSex(rs.getString(6).charAt(0));
				user.setType(rs.getString(7).charAt(0));
				user.setRegDate(rs.getDate(8));
				user.setRegTime(rs.getTime(9));
				user.setBirthDate(rs.getDate(10));
				user.setPhone(rs.getString(11));
				user.setEmail(rs.getString(12));
				user.setAbout(rs.getString(13));
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
		return user;
	}

	@Override
	public Discount getCurrentUserDiscountByID(int id) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_CURRENT_DISCOUNT_BY_USER_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Discount discount = new Discount();
				discount.setId(rs.getInt(1));
				discount.setUserID(rs.getInt(2));
				discount.setAmount(rs.getInt(3));
				discount.setStDate(rs.getDate(4));
				discount.setStTime(rs.getTime(5));
				discount.setEnDate(rs.getDate(6));
				discount.setEnTime(rs.getTime(7));
				return discount;
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
		return null;
	}

	@Override
	public void banUser(int userID, Date startDate, Time startTime, int length, String reason) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;	
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(INSERT_BAN_RECORD);
			st.setInt(1, userID);
			st.setDate(2, startDate);
			st.setTime(3, startTime);
			st.setInt(4, length);
			st.setString(5, reason);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(ExceptionMessages.SQL_INSERT_FAILURE, e);
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
	public void unbanUser(int userID) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;	
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(UNBAN_USER_BY_ID);
			st.setInt(1, userID);
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
	public String getCurrentBanEnd(int userID) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_CURRENT_BAN_END_BY_ID);
			st.setInt(1, userID);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1) + " " + rs.getString(2);
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
		return null;
	}

	@Override
	public String getCurrentBanReason(int userID) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_CURRENT_BAN_REASON_BY_ID);
			st.setInt(1, userID);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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
		return null;
	}

	@Override
	public int addDiscount(Discount discount) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(INSERT_NEW_DISCOUNT);
			st.setInt(1, discount.getUserID());
			st.setInt(2, discount.getAmount());
			st.setDate(3, discount.getStDate());
			st.setTime(4, discount.getStTime());
			st.setDate(5, discount.getEnDate());
			st.setTime(6, discount.getEnTime());
			st.executeUpdate();
			
			st2 = con.prepareStatement(SELECT_NEW_DISCOUNT_ID);
			st2.setInt(1, discount.getUserID());
			st2.setInt(2, discount.getAmount());
			st2.setDate(3, discount.getStDate());
			st2.setTime(4, discount.getStTime());
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
				if (st2 != null) { st2.close(); }
				if (rs != null) { rs.close(); }
			} catch (SQLException e) {
				throw new DAOException(ExceptionMessages.RS_OR_STATEMENT_NOT_CLOSED, e);
			} finally {
				if (con != null) { pool.closeConnection(con); }
			}
		}
		return 0;
	}

	@Override
	public void editDiscount(Discount editedDisc) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;	
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(UPDATE_DISCOUNT);
			st.setInt(1, editedDisc.getAmount());
			st.setDate(2, editedDisc.getEnDate());
			st.setTime(3, editedDisc.getEnTime());
			st.setInt(4, editedDisc.getId());
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
	public void deleteDiscount(int discountID) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;	
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(DELETE_DISCOUNT);
			st.setInt(1, discountID);
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
	public Discount getDiscountByID(int discountID) throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_DISCOUNT_BY_ID);
			
			st.setInt(1, discountID);
			rs = st.executeQuery();
			if (rs.next()) {
				Discount discount = new Discount();
				discount.setId(rs.getInt(1));
				discount.setUserID(rs.getInt(2));
				discount.setAmount(rs.getInt(3));
				discount.setStDate(rs.getDate(4));
				discount.setStTime(rs.getTime(5));
				discount.setEnDate(rs.getDate(6));
				discount.setEnTime(rs.getTime(7));
				return discount;
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
		return null;
	}

	@Override
	public Set<User> getAllUsersPart(int start, int amount) throws DAOException {
		Set<User> userSet = new LinkedHashSet<User>();
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_ALL_USERS_PART);
			st.setInt(1, start);
			st.setInt(2, amount);
			rs = st.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurname(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setSex(rs.getString(6).charAt(0));
				user.setType(rs.getString(7).charAt(0));
				user.setRegDate(rs.getDate(8));
				user.setRegTime(rs.getTime(9));
				user.setBirthDate(rs.getDate(10));
				user.setPhone(rs.getString(11));
				user.setEmail(rs.getString(12));
				user.setAbout(rs.getString(13));
				
				userSet.add(user);
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
		return userSet;
	}

	@Override
	public int getNumberOfUsers() throws DAOException {
		ConnectionPool pool = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnection();
			st = con.prepareStatement(SELECT_ALL_USERS_COUNT);
			
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
