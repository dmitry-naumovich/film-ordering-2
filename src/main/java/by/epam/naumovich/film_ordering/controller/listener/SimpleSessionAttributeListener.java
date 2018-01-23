package by.epam.naumovich.film_ordering.controller.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import lombok.extern.slf4j.Slf4j;
/**
 * Logs session attribute adding and removing events
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class SimpleSessionAttributeListener implements HttpSessionAttributeListener {

	private static final String ATTRIBUTE_ADDED = "Session attribute added: ";
	private static final String ATTRIBUTE_REMOVED = "Session attribute removed: ";
	private static final String COLON = " : ";

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {	
		log.debug(ATTRIBUTE_ADDED + arg0.getClass().getSimpleName() + COLON + arg0.getName() + COLON + arg0.getValue());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		log.debug(ATTRIBUTE_REMOVED + arg0.getClass().getSimpleName() + COLON + arg0.getName() + COLON + arg0.getValue());

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {}

}
