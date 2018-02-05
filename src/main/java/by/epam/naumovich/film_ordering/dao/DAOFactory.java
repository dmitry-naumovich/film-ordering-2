package by.epam.naumovich.film_ordering.dao;

import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import by.epam.naumovich.film_ordering.dao.impl.MySQLDAOFactory;
import by.epam.naumovich.film_ordering.dao.util.ExceptionMessages;

/**
 * Produces DAO relevant factories of different types depending on the data source type
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public abstract class DAOFactory {
	
	/**
	 * Enumeration of available and implemented data source types
	 * 
	 * @author Dmitry
	 * @version 1.0
	 */
	private enum DBType {
		MYSQL
	}
	
	/**
	 * Return a DAOFactory depending on the data source type
	 * 
	 * @param type the type of the data source
	 * @return DAOFactory implementation
	 * @throws DAOException if specified database type is unknown
	 */
	public static DAOFactory getDAOFactory(String type) throws DAOException { 
		DBType dbType = DBType.valueOf(type.toUpperCase());
		switch (dbType) {
			case MYSQL: 
				return MySQLDAOFactory.getInstance();							
			default : 
				throw new DAOException(ExceptionMessages.UNKNOWN_DATA_SOURCE);
		}
	}
}
