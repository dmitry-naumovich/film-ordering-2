package by.epam.naumovich.film_ordering.command.impl.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Performs the command that gets a single order from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenSingleOrder implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		String lang = null;
		try {
			lang = session.getAttribute(RequestAndSessionAttributes.LANGUAGE).toString();
		} catch (NullPointerException e) {
			lang = RequestAndSessionAttributes.ENG_LANG;
		}
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_SINGLE_ORDER);
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
		}
		else {
			int orderNum = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.ORDER_NUM));
			try {
				IOrderService orderService = ServiceFactory.getInstance().getOrderService();
				IFilmService filmService = ServiceFactory.getInstance().getFilmService();
				IUserService userService = ServiceFactory.getInstance().getUserService();
				Order order = orderService.getOrderByOrderNum(orderNum);
				String filmName = filmService.getFilmNameByID(order.getFilmId(), lang);
				String userLogin = userService.getLoginByID(order.getUserId());
				
				request.setAttribute(RequestAndSessionAttributes.ORDER, order);
				request.setAttribute(RequestAndSessionAttributes.FILM_NAME, filmName);
				request.setAttribute(RequestAndSessionAttributes.USER_LOGIN, userLogin);
				request.getRequestDispatcher(JavaServerPageNames.SINGLE_ORDER_PAGE).forward(request, response);
			} catch (ServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}