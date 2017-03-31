package by.epam.naumovich.film_ordering.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Performs the command that reads user ID parameter from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class UnbanUser implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		int userID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.USER_ID));
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.UNBAN_USER_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
		}
		else if (!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.UNBAN_USER_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
		}
		else {
			try {
				IUserService userService = ServiceFactory.getInstance().getUserService();
				userService.unbanUser(userID);
				LOGGER.debug(String.format(LogMessages.USER_UNBANNED, userID));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.USER_UNBANNED);
				Thread.sleep(1000);
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
			} catch (ServiceException | InterruptedException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
