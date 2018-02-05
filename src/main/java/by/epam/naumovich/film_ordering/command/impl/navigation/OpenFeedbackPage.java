package by.epam.naumovich.film_ordering.command.impl.navigation;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that forwards request and response to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenFeedbackPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		setPrevQueryAttributeToSession(request, session, log);
		request.getRequestDispatcher(JavaServerPageNames.FEEDBACK_PAGE).forward(request, response);
	}

}
