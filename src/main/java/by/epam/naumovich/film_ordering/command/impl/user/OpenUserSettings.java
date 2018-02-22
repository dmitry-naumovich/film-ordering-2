package by.epam.naumovich.film_ordering.command.impl.user;

import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.USER_ID;

/**
 * Performs the command that gets user entity from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenUserSettings implements Command {

	private final IUserService userService;

	public OpenUserSettings(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);
		
		if (!isAuthorized(session)) {
			if (request.getParameter(USER_ID).isEmpty() || request.getParameter(USER_ID) == null) {
				request.setAttribute(ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_SETTINGS);
			}
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
			
		}
		else {
			int userId; //todo: consider
			if (!session.getAttribute(USER_ID).toString().equals(request.getParameter(USER_ID))) {
				userId = fetchUserIdFromSession(session);
			}
			else {
				userId = fetchUserIdFromRequest(request);
			}
			User user = userService.getByLogin(userService.getLoginByID(userId));
			request.setAttribute(RequestAndSessionAttributes.USER, user);
			request.getRequestDispatcher(JavaServerPageNames.PROFILE_SETTINGS_PAGE).forward(request, response);
		}
	}
}
