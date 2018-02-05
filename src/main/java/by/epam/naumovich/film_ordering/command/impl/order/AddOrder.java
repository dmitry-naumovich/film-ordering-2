package by.epam.naumovich.film_ordering.command.impl.order;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.AddOrderServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that reads new order parameters from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class AddOrder implements Command {

	private final IOrderService orderService;

	public AddOrder(IOrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_ORDER_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else if (isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADMIN_CAN_NOT_ORDER);
			request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" +
                    Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID)) + "&pageNum=1");
		}
		else {
			int filmID = fetchFilmIdFromRequest(request);
			int userID = fetchUserIdFromRequest(request);

			String price = request.getParameter(RequestAndSessionAttributes.PRICE);
			String discount = request.getParameter(RequestAndSessionAttributes.DISCOUNT);
			String payment = request.getParameter(RequestAndSessionAttributes.PAYMENT);
			
			try {
				int orderNum = orderService.create(filmID, userID, price, discount, payment);
				log.debug(String.format(LogMessages.ORDER_CREATED, orderNum, payment));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.ORDER_ADDED);
				request.getRequestDispatcher("/Controller?command=open_single_order&orderNum=" + orderNum)
                        .forward(request, response);
			} catch (AddOrderServiceException e) {
				log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_new_order_page&filmID=" + filmID)
                        .forward(request, response);
			}
		}
	}

}
