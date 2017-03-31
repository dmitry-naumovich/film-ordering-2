package by.epam.naumovich.film_ordering.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;

/**
 * Performs the command that changes the current session language to another one
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class ChangeLanguage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String language = request.getParameter(RequestAndSessionAttributes.LANGUAGE);
		request.getSession().setAttribute(RequestAndSessionAttributes.LANGUAGE, language);
		
		String prev_query = (String) request.getSession(false).getAttribute(RequestAndSessionAttributes.PREV_QUERY);
		
		if (prev_query != null) {
			response.sendRedirect(prev_query);
		}
		else {
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		}
	}

}
