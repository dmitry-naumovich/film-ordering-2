package by.epam.naumovich.film_ordering.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logs HTTP session creating and destroying events and prints jsessionID
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class SimpleSessionListener implements HttpSessionListener {

	private static final Logger logger = LogManager.getLogger(Logger.class.getName());
	private static final String SESSION_CREATED = "Session created: jsessionID = ";
	private static final String SESSION_DESTROYED = "Session destroyed: jsessionID = ";
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		logger.debug(SESSION_CREATED + arg0.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		logger.debug(SESSION_DESTROYED + arg0.getSession().getId());
	}

}
