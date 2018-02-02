package by.epam.naumovich.film_ordering.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Logs HTTP session creating and destroying events and prints jsessionID
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Component
@Slf4j
public class SimpleSessionListener implements HttpSessionListener {

	private static final String SESSION_CREATED = "Session created: jsessionID = ";
	private static final String SESSION_DESTROYED = "Session destroyed: jsessionID = ";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug(SESSION_CREATED + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug(SESSION_DESTROYED + se.getSession().getId());
    }
}
