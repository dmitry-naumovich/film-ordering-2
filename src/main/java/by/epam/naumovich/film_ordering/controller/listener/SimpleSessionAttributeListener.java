package by.epam.naumovich.film_ordering.controller.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Logs session attribute adding and removing events
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Component
@Slf4j
public class SimpleSessionAttributeListener implements HttpSessionAttributeListener {

	private static final String ATTRIBUTE_ADDED = "Session attribute added: ";
	private static final String ATTRIBUTE_REMOVED = "Session attribute removed: ";
	private static final String COLON = " : ";

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		log.debug(ATTRIBUTE_ADDED + se.getClass().getSimpleName() + COLON + se.getName() + COLON + se.getValue());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		log.debug(ATTRIBUTE_REMOVED + se.getClass().getSimpleName() + COLON + se.getName() + COLON + se.getValue());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {

	}
}
