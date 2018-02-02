package by.epam.naumovich.film_ordering.dao.impl;

import by.epam.naumovich.film_ordering.dao.DAOFactory;
import by.epam.naumovich.film_ordering.dao.IOrderDAO;
import by.epam.naumovich.film_ordering.dao.IReviewDAO;

/**
 * DAOFactory that works with the MySQL database as data source and overrides abstract methods from DAOFactory
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class MySQLDAOFactory extends DAOFactory {

	/**
	 * Singleton instance of MySQLDAOFactory that will be used by all concrete DAO classes
	 * 
	 */
	private static final MySQLDAOFactory instance = new MySQLDAOFactory();
	
	private MySQLDAOFactory() { }

	public static DAOFactory getInstance() {
		return instance;
	}

	@Override
	public IOrderDAO getOrderDAO() {
		return MySQLOrderDAO.getInstance();
	}

	@Override
	public IReviewDAO getReviewDAO() {
		return MySQLReviewDAO.getInstance();
	}
}
