package by.epam.naumovich.film_ordering.command;

import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ENG_LANG;

/**
 * Defines methods for overriding in all Command implementations which should perform a command with help of which
 * the Controller sends and received data to/from the service layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@FunctionalInterface
public interface Command {
	/**
	 * Proceeds the request, sends data to and receive it from the service layer and then forwards
	 * both request and response to the JSP page or to the Controller servlet which will execute another command
	 * 
	 * @param request servlet request
	 * @param response servlet response
	 * @throws IOException
	 * @throws ServletException
	 */
	void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	default String fetchLanguageFromSession(HttpSession session) {
        Object languageAttribute = session.getAttribute(RequestAndSessionAttributes.LANGUAGE);
        return languageAttribute == null ? ENG_LANG : languageAttribute.toString();
    }

    default boolean isAuthorized(HttpSession session) {
        return session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) != null;
    }

    default boolean isAdmin(HttpSession session) {
        return Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString());
    }

    default int fetchUserIdFromSession(HttpSession session) {
        return Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
    }

    default int fetchUserIdFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter(RequestAndSessionAttributes.USER_ID));
    }

    default int fetchFilmIdFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID));
    }

    default int fetchPageNumberFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter(RequestAndSessionAttributes.PAGE_NUM));
    }
}
