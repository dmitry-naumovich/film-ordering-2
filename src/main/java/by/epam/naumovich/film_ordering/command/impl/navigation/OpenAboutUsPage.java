package by.epam.naumovich.film_ordering.command.impl.navigation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;

/**
 * Performs the command that forwards request and response to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenAboutUsPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query = QueryUtil.createHttpQueryString(request);
		request.getSession(true).setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		//define language and take about us text from database in appropriate language
		request.getRequestDispatcher(JavaServerPageNames.ABOUT_US_PAGE).forward(request, response);
	}

}
