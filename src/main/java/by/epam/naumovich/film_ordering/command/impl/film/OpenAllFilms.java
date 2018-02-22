package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets all films from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenAllFilms implements Command {

	private final IFilmService filmService;
	private final IOrderService orderService;

	public OpenAllFilms(IFilmService filmService, IOrderService orderService) {
		this.filmService = filmService;
		this.orderService = orderService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {
        setPrevQueryAttributeToSession(request, session, log);

		String lang = fetchLanguageFromSession(session);
		int pageNum = fetchPageNumberFromRequest(request);

		try {
			List<Film> films = filmService.getAllPart(pageNum, lang);
			request.setAttribute(RequestAndSessionAttributes.FILMS, films);
			
			long totalPageAmount = filmService.countPages();
			request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
			request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
			
			if (isAuthorized(session) && !isAdmin(session)) {
                int userId = fetchUserIdFromSession(session);
                try {
                    List<Order> orders = orderService.getAllByUserId(userId);
                    List<Integer> orderFilmIDs = orders.stream().map(Order::getFilmId).collect(Collectors.toList());
                    request.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, orderFilmIDs);
                } catch (GetOrderServiceException e) {
                    request.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, Collections.emptyList());
					}
			}
			
			request.getRequestDispatcher(JavaServerPageNames.FILMS_JSP_PAGE).forward(request, response);
			
		} catch (GetFilmServiceException e) {
			log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                    e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.FILMS_JSP_PAGE).forward(request, response);
		}
	}

}
