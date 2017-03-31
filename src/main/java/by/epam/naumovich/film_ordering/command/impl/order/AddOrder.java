package by.epam.naumovich.film_ordering.command.impl.order;

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
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.AddOrderServiceException;

/**
 * Performs the command that reads new order parameters from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class AddOrder implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.ADD_ORDER_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
		}
		else if (Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.ADMIN_CAN_NOT_ORDER);
			request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID)) + "&pageNum=1");
		}
		else {
			int filmID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID));
			int userID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.USER_ID));
			String price = request.getParameter(RequestAndSessionAttributes.PRICE);
			String discount = request.getParameter(RequestAndSessionAttributes.DISCOUNT);
			String payment = request.getParameter(RequestAndSessionAttributes.PAYMENT);
			
			try {
				IOrderService orderService = ServiceFactory.getInstance().getOrderService();
				int orderNum = orderService.addOrder(filmID, userID, price, discount, payment);
				LOGGER.debug(String.format(LogMessages.ORDER_ADDED, orderNum, payment));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.ORDER_ADDED);
				request.getRequestDispatcher("/Controller?command=open_single_order&orderNum=" + orderNum).forward(request, response);
			} catch (AddOrderServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_new_order_page&filmID=" + filmID).forward(request, response);
			} catch (ServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}

	}

}
