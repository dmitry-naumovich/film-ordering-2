package by.epam.naumovich.film_ordering.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that invalidates current user session.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class Logout implements Command {
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String prev_query = (String) session.getAttribute(RequestAndSessionAttributes.PREV_QUERY);
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) != null) {
			String login = session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER).toString();
			int id = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
			log.debug(String.format(LogMessages.USER_LOGGED_OUT, login, id));
			session.invalidate();
		}
		
		if (prev_query != null) {
			response.sendRedirect(prev_query);
		}
		else {
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		}
	}
}
