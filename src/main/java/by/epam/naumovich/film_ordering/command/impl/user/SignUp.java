package by.epam.naumovich.film_ordering.command.impl.user;

import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IUserFileUploadService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceSignUpException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.SUCCESS_MESSAGE;

/**
 * Performs the command that reads new user parameters from the JSP and sends them to the relevant service class.
 * Uploads images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class SignUp implements Command {

	private final IUserService userService;
	private final IUserFileUploadService fileUploadService;

	public SignUp(IUserService userService, IUserFileUploadService fileUploadService) {
		this.userService = userService;
		this.fileUploadService = fileUploadService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		if (isAuthorized(session)) {
			int userID = fetchUserIdFromSession(session);
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.LOG_OUT_FOR_SIGN_UP);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID)
                    .forward(request, response);
		} else {
			try {
                Pair<Integer, String> userIdAndLogin = fileUploadService.storeFilesAndAddUser(request);
                int userID = userIdAndLogin.getFirst();
                String login = userIdAndLogin.getSecond();

				User user = userService.getByLogin(login);

				session.setAttribute(RequestAndSessionAttributes.AUTHORIZED_USER, login);
				session.setAttribute(RequestAndSessionAttributes.USER_ID, user.getId());
				session.setAttribute(RequestAndSessionAttributes.IS_ADMIN, 'a' == user.getType());

				log.debug(String.format(LogMessages.USER_CREATED, login, userID));
				request.setAttribute(SUCCESS_MESSAGE, SuccessMessages.SUCCESSFUL_SIGN_UP);
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);

			} catch (ServiceSignUpException e) {
				log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_sign_up_page").forward(request, response);
			} catch (Exception e) {
				log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
