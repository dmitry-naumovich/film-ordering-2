package by.epam.naumovich.film_ordering.command.impl.film;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that gets twelve last added films from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class GetNovelty implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);

		String lang;
		try {
			lang = session.getAttribute(RequestAndSessionAttributes.LANGUAGE).toString();
		} catch (NullPointerException e) {
			lang = RequestAndSessionAttributes.ENG_LANG;
		}
		
		try {
			IFilmService filmService = ServiceFactory.getInstance().getFilmService();
			IOrderService orderService = ServiceFactory.getInstance().getOrderService();
			
			Set<Film> filmSet = filmService.getTwelveLastAddedFilms(lang);
			//request.setAttribute(RequestAndSessionAttributes.NOVELTY_LIST, filmSet);
			session.setAttribute(RequestAndSessionAttributes.NOVELTY_LIST, filmSet);

			if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) != null) {
				if (!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
					int userID = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
					try {
						Set<Order> orders = orderService.getOrdersByUserId(userID);
						List<Integer> orderFilmIDs = orders.stream().map(Order::getFilmId).collect(Collectors.toList());
						request.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, orderFilmIDs);
					} catch (GetOrderServiceException e) {
						request.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, Collections.emptyList());
					}
				}
			}
		} catch (GetFilmServiceException e) {
			log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		}
		catch (ServiceException e) {
			e.printStackTrace();
			log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}
	}
	

}
