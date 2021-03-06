package by.epam.naumovich.film_ordering.command.impl.user;

import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceAuthException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that authorize user reading the parameters from the JSP and sending them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class Login implements Command {

	private final IUserService userService;

	public Login(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		if (isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.LOG_OUT_FOR_ANOTHER_ACC);
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		}
		else {
			String login = request.getParameter(RequestAndSessionAttributes.LOGIN);
			String password = request.getParameter(RequestAndSessionAttributes.PASSWORD);
			
			try {
				User user = userService.authenticate(login, password);

				log.debug(String.format(LogMessages.USER_LOGGED_IN, login, user.getId()));
				request.setAttribute(RequestAndSessionAttributes.USER, user);
				session.setAttribute(RequestAndSessionAttributes.AUTHORIZED_USER, user.getLogin());
				session.setAttribute(RequestAndSessionAttributes.USER_ID, user.getId());
				session.setAttribute(RequestAndSessionAttributes.IS_ADMIN, 'a' == user.getType());
				
				String prevQuery = (String) session.getAttribute(RequestAndSessionAttributes.PREV_QUERY);
				if (prevQuery != null) {
					response.sendRedirect(prevQuery);
				}
				else {
					request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
				}
			} catch (ServiceAuthException e) {
				log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
			}
		}
	}
}
