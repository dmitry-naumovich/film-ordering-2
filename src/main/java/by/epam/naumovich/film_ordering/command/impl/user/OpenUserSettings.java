package by.epam.naumovich.film_ordering.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that gets user entity from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenUserSettings implements Command {


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			if (request.getParameter(RequestAndSessionAttributes.USER_ID).isEmpty() || request.getParameter(RequestAndSessionAttributes.USER_ID) == null) {
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_SETTINGS);
			}
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
			
		}
		else {
			int userID;
			if (!session.getAttribute(RequestAndSessionAttributes.USER_ID).toString().equals(request.getParameter(RequestAndSessionAttributes.USER_ID))) {
				userID = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
			}
			else {
				userID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.USER_ID));
			}
			IUserService userService = ServiceFactory.getInstance().getUserService();
			
			try {
				User user = userService.getUserByLogin(userService.getLoginByID(userID));
				request.setAttribute(RequestAndSessionAttributes.USER, user);
				request.getRequestDispatcher(JavaServerPageNames.PROFILE_SETTINGS_PAGE).forward(request, response);	
			} catch (ServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
