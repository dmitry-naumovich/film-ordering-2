package by.epam.naumovich.film_ordering.command.impl.navigation;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that forwards request and response to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenLoginPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {

        setPrevQueryAttributeToSession(request, session, log);
		
		if (isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.LOG_OUT_FOR_ANOTHER_ACC);
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		} else {
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
	}
}
