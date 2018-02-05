package by.epam.naumovich.film_ordering.command.impl.user;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.SUCCESS_MESSAGE;

/**
 * Performs the command that reads user ID parameter from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class UnbanUser implements Command {

	private final IUserService userService;

	public UnbanUser(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		int userID = fetchUserIdFromRequest(request);
		
		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.UNBAN_USER_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else if (!isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.UNBAN_USER_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID)
                    .forward(request, response);
		}
		else {
            userService.unbanUser(userID);

            log.debug(String.format(LogMessages.USER_UNBANNED, userID));
            request.setAttribute(SUCCESS_MESSAGE, SuccessMessages.USER_UNBANNED);
            //Thread.sleep(1000);
            request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID)
                    .forward(request, response);
		}
	}
}
