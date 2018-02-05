package by.epam.naumovich.film_ordering.command.impl.user.discount;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IDiscountService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.DiscountServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.SUCCESS_MESSAGE;

/**
 * Performs the command that reads new discount parameters from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class AddDiscount implements Command {

	private final IDiscountService discountService;

	public AddDiscount(IDiscountService discountService) {
		this.discountService = discountService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

		int userID = fetchUserIdFromRequest(request);
		
		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_DISCOUNT_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else if (!isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_DISCOUNT_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID)
                    .forward(request, response);
		}
		else {
			String amount = request.getParameter(RequestAndSessionAttributes.AMOUNT);
			String endDate = request.getParameter(RequestAndSessionAttributes.END_DATE);
			String endTime = request.getParameter(RequestAndSessionAttributes.END_TIME);
			
			try {
				discountService.create(userID, amount, endDate, endTime);
				log.debug(String.format(LogMessages.DISCOUNT_CREATED, userID, amount));
				request.setAttribute(SUCCESS_MESSAGE, SuccessMessages.DISCOUNT_ADDED);
				//Thread.sleep(1000);
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID)
                        .forward(request, response);
			} catch (DiscountServiceException e) {
				log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID)
                        .forward(request, response);
			}
		}
	}
}
