package by.epam.naumovich.film_ordering.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import by.epam.naumovich.film_ordering.dao.pool.exception.ConnectionPoolException;
import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ConnectionPool {


	private static ConnectionPool instance;
	
	private Queue<Connection> freeConnections;
	private Queue<Connection> busyConnections;
	
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	
	
	public synchronized static ConnectionPool getInstance() throws ConnectionPoolException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	private ConnectionPool() throws ConnectionPoolException {
		freeConnections = new LinkedList<>();
		busyConnections = new LinkedList<>();
		
		DBResourceManager manager = DBResourceManager.getInstance();
		
		this.driverName = manager.getValue(DBParameter.DB_DRIVER);
		this.url = manager.getValue(DBParameter.DB_URL);
		this.user = manager.getValue(DBParameter.DB_USER);
		this.password = manager.getValue(DBParameter.DB_PASSWORD);
		try {
			this.poolSize = Integer.parseInt(manager.getValue(DBParameter.DB_POOL_SIZE));
			Class.forName(driverName);
		} catch (NumberFormatException e) {
			log.error(ExceptionMessages.INVALID_INITIALIZATION_PARAMETER, e);
			poolSize = 100;
		} catch (ClassNotFoundException e) {
			log.error(ExceptionMessages.INVALID_INITIALIZATION_PARAMETER, e);
			throw new ConnectionPoolException(ExceptionMessages.SQL_EXCEPTION_IN_POOL, e);
		}
		
 		Connection connection;
		try {
			connection = DriverManager.getConnection(url, user, password);
			freeConnections.add(connection); 
		} catch (SQLException e) {
			log.error(ExceptionMessages.CONNECTION_NOT_ADDED, e);
			throw new ConnectionPoolException(ExceptionMessages.CONNECTION_NOT_ADDED);
		} 
 	 		
	}
	
	public synchronized Connection getConnection() throws ConnectionPoolException {
		if (freeConnections.isEmpty() && busyConnections.isEmpty()) {
			fillEmptyConnectionList();
		}
		else if (freeConnections.isEmpty() && !busyConnections.isEmpty()) {
			addOneNewConnection();
		}
		Connection con = freeConnections.poll();
		busyConnections.add(con);
		return con;
	}
	
	private void fillEmptyConnectionList() throws ConnectionPoolException {
		try {
			Connection con;
			for (int i = 0; i < poolSize; i++) {
				con = DriverManager.getConnection(url, user, password);
				freeConnections.add(con);
				
			}
		} catch (SQLException e) {
			log.error(ExceptionMessages.SQL_EXCEPTION_IN_POOL, e);
			throw new ConnectionPoolException(ExceptionMessages.SQL_EXCEPTION_IN_POOL, e);
		}
	}
	
	private void addOneNewConnection() throws ConnectionPoolException  {
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			freeConnections.add(con);
		} catch (SQLException e) {
			log.error(ExceptionMessages.SQL_EXCEPTION_IN_POOL, e);
			throw new ConnectionPoolException(ExceptionMessages.SQL_EXCEPTION_IN_POOL, e);
		}
		
	}

	public void closeConnection(Connection con) {
		try {
			freeConnection(con);
		} catch (SQLException e) {
			log.error(ExceptionMessages.CONNECTION_NOT_CLOSED, e);
		}
	}
	
	private synchronized void freeConnection(Connection con) throws SQLException {
		if (con.isClosed()) {
			throw new SQLException(ExceptionMessages.IMPOSSIBLE_TO_CLOSE);
		}
		if (!con.getAutoCommit()) {
			con.commit();
		}
		if (!busyConnections.remove(con)) {
			throw new SQLException(ExceptionMessages.CONNECTION_NOT_FOUND);
		}
		
		if (!freeConnections.offer(con)) {
			throw new SQLException(ExceptionMessages.CONNECTION_NOT_ALLOCATED);
		}
	}
}