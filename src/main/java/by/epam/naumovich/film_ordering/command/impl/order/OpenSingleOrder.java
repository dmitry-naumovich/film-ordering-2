package by.epam.naumovich.film_ordering.command.impl.order;

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
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets a single order from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenSingleOrder implements Command {

	private final IFilmService filmService;
	private final IOrderService orderService;
	private final IUserService userService;

	public OpenSingleOrder(IFilmService filmService, IOrderService orderService, IUserService userService) {
		this.filmService = filmService;
		this.orderService = orderService;
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		log.info(query);

		String lang = fetchLanguageFromSession(session);
		
		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_SINGLE_ORDER);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
			int orderNum = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.ORDER_NUM));
            Order order = orderService.getByOrderNum(orderNum);
            String filmName = filmService.getNameByID(order.getFilmId(), lang);
            String userLogin = userService.getLoginByID(order.getUserId());

            request.setAttribute(RequestAndSessionAttributes.ORDER, order);
            request.setAttribute(RequestAndSessionAttributes.FILM_NAME, filmName);
            request.setAttribute(RequestAndSessionAttributes.USER_LOGIN, userLogin);
            request.getRequestDispatcher(JavaServerPageNames.SINGLE_ORDER_PAGE).forward(request, response);
		}
	}
}