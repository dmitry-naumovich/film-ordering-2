package by.epam.naumovich.film_ordering.command.impl.user;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {

		String prevQuery = (String) session.getAttribute(RequestAndSessionAttributes.PREV_QUERY);
		
		if (isAuthorized(session)) {
			String login = session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER).toString();
			int userId = fetchUserIdFromSession(session);
			log.debug(String.format(LogMessages.USER_LOGGED_OUT, login, userId));
			session.invalidate();
		}
		
		if (prevQuery != null) {
			response.sendRedirect(prevQuery);
		}
		else {
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		}
	}
}
