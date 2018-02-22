package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
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
 * Performs the command that gets all films that satisfies search criterion from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class SearchFilms implements Command {

	private final IFilmService filmService;
	private final IOrderService orderService;

    public SearchFilms(IFilmService filmService, IOrderService orderService) {
        this.filmService = filmService;
        this.orderService = orderService;
    }

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);

		String lang = fetchLanguageFromSession(session);
		String text = request.getParameter(RequestAndSessionAttributes.SEARCH_TEXT);

		if (text.length() == 0) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.NOTHING_FOUND);
		} else {
			try {
				List<Film> foundFilms = filmService.searchByName(text, lang);
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.FILMS_FOUND);
				request.setAttribute(RequestAndSessionAttributes.FILMS, foundFilms);
				
				if (isAuthorized(session)) {
					if (!isAdmin(session)) {
						int userId = fetchUserIdFromSession(session);
						try {
							List<Order> orders = orderService.getAllByUserId(userId);
							List<Integer> orderFilmIDs = orders.stream().map(Order::getFilmId).collect(Collectors.toList());
							request.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, orderFilmIDs);
						} catch (GetOrderServiceException e) {
							request.setAttribute(RequestAndSessionAttributes.USER_ORDER_FILM_IDS, Collections.emptyList());
						}
					}
				}
				request.getRequestDispatcher(JavaServerPageNames.FILMS_JSP_PAGE).forward(request, response);
			} catch (GetFilmServiceException e) {
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
                request.getRequestDispatcher(JavaServerPageNames.FILMS_JSP_PAGE).forward(request, response);
            }
		}

	}

}
