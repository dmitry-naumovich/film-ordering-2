package by.epam.naumovich.film_ordering.command.impl.order;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.USER_ID;

/**
 * Performs the command that gets all concrete user orders from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenUserOrders implements Command {

	private final IFilmService filmService;
	private final IOrderService orderService;
	private final IUserService userService;

	public OpenUserOrders(IFilmService filmService, IOrderService orderService, IUserService userService) {
		this.filmService = filmService;
		this.orderService = orderService;
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);
		String lang = fetchLanguageFromSession(session);
		int pageNum = fetchPageNumberFromRequest(request);
		
		if (!isAuthorized(session)) {
			if (request.getParameter(USER_ID).isEmpty() || request.getParameter(USER_ID) == null) {
				request.setAttribute(ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_YOUR_ORDERS);
			}
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
            int userId = fetchUserIdFromSession(session);

			if (isAdmin(session)) {
			  userId = fetchUserIdFromRequest(request);
            }

			try {
				List<Order> orders = orderService.getAllPartByUserId(userId, pageNum);
				
				List<String> filmNames = new ArrayList<>();
				for (Order o : orders) {
					String filmName = filmService.getNameByID(o.getFilmId(), lang);
					filmNames.add(filmName);
				}
				String userLogin = userService.getLoginByID(userId);

				long totalPageAmount = orderService.countPagesByUserId(userId);
				request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
				request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
				
				request.setAttribute(RequestAndSessionAttributes.ORDERS, orders);
				request.setAttribute(RequestAndSessionAttributes.FILM_NAMES, filmNames);
				request.setAttribute(RequestAndSessionAttributes.USER_LOGIN, userLogin);
				request.setAttribute(USER_ID, userId);
				request.setAttribute(RequestAndSessionAttributes.ORDER_VIEW_TYPE, RequestAndSessionAttributes.VIEW_TYPE_USER);
				request.getRequestDispatcher(JavaServerPageNames.ORDERS_PAGE).forward(request, response);
				
			} catch (GetOrderServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
						e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_user_profile&userId=" + userId)
						.forward(request, response);
			}
		}
	}
}
