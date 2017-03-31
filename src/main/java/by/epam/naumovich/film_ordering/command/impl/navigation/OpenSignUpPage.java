package by.epam.naumovich.film_ordering.command.impl.navigation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;

/**
 * Performs the command that forwards request and response to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenSignUpPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) != null) {
			int userID = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.LOG_OUT_FOR_SIGN_UP);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
		} else {
			request.getRequestDispatcher(JavaServerPageNames.SIGN_UP_PAGE).forward(request, response);
		}
	}

}
