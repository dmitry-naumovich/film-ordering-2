package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.*;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that gets a single film from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenSingleFilm implements Command {

	private final IFilmService filmService;
	private final IOrderService orderService;
	private final IReviewService reviewService;
	private final IUserService userService;

    public OpenSingleFilm(IFilmService filmService, IOrderService orderService, IReviewService reviewService, IUserService userService) {
        this.filmService = filmService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);

		String lang = fetchLanguageFromSession(session);
		
		int filmID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID));
		int pageNum = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.PAGE_NUM));
		
		try {
			Film film = filmService.getByID(filmID, lang);
			request.setAttribute(RequestAndSessionAttributes.FILM, film);
			
			if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) != null) {
				if (!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
					int userID = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
					try {
						reviewService.getByUserAndFilmId(userID, filmID);
						request.setAttribute(RequestAndSessionAttributes.OWN_REVIEW_EXISTS, true);
					} catch (GetReviewServiceException e) {
						request.setAttribute(RequestAndSessionAttributes.OWN_REVIEW_EXISTS, false);
					}
					
					try {
						Order order = orderService.getByUserAndFilmId(userID, filmID);
						request.setAttribute(RequestAndSessionAttributes.OWN_ORDER_EXISTS, true);
						request.setAttribute(RequestAndSessionAttributes.ORDER_NUM, order.getOrdNum());
					} catch (GetOrderServiceException e) {
						request.setAttribute(RequestAndSessionAttributes.OWN_ORDER_EXISTS, false);
					}
					
				}
			}
			
			List<Review> reviews = reviewService.getAllPartByFilmId(filmID, pageNum);
			request.setAttribute(RequestAndSessionAttributes.REVIEWS, reviews);
			request.setAttribute(RequestAndSessionAttributes.REVIEW_VIEW_TYPE, RequestAndSessionAttributes.VIEW_TYPE_FILM);
			
			long totalPageAmount = reviewService.countByFilmId(filmID);
			request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
			request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
			
			List<String> reviewLogins = new ArrayList<>();
			for (Review r : reviews) {
				reviewLogins.add(userService.getLoginByID(r.getAuthor()));
			}
			request.setAttribute(RequestAndSessionAttributes.LOGINS, reviewLogins);
			request.getRequestDispatcher(JavaServerPageNames.SINGLE_FILM_PAGE).forward(request, response);
		
		} catch (GetReviewServiceException e) {
			request.getRequestDispatcher(JavaServerPageNames.SINGLE_FILM_PAGE).forward(request, response);
		}
		catch (ServiceException e) {
			log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}

	}

}
