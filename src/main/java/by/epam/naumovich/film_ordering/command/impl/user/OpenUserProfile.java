package by.epam.naumovich.film_ordering.command.impl.user;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IDiscountService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetDiscountServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetUserServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets a single user from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenUserProfile implements Command {

	private final IUserService userService;
	private final IDiscountService discountService;

	public OpenUserProfile(IUserService userService, IDiscountService discountService) {
		this.userService = userService;
		this.discountService = discountService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

        setPrevQueryAttributeToSession(request, session, log);
		
		if (request.getParameter(RequestAndSessionAttributes.USER_ID).equals(RequestAndSessionAttributes.EMPTY_STRING)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_PROFILE);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
			int userID = fetchUserIdFromRequest(request);
			try {
				User user = userService.getByID(userID);

				request.setAttribute(RequestAndSessionAttributes.USER, user);
				request.setAttribute(RequestAndSessionAttributes.BANNED, userService.isBanned(userID));

				try {
					Discount discount = discountService.getCurrentUserDiscountByID(userID);
					request.setAttribute(RequestAndSessionAttributes.USER_DISCOUNT, discount);
				} catch (GetDiscountServiceException e) {}
				
				request.getRequestDispatcher(JavaServerPageNames.PROFILE_PAGE).forward(request, response);	
			} catch (GetUserServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.PROFILE_PAGE).forward(request, response);
			}
		}
	}

}
