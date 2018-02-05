package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

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

    public OpenSingleFilm(IFilmService filmService, IOrderService orderService, IReviewService reviewService,
						  IUserService userService) {
        this.filmService = filmService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

        setPrevQueryAttributeToSession(request, session, log);

		String lang = fetchLanguageFromSession(session);
		int filmID = fetchFilmIdFromRequest(request);
		int pageNum = fetchPageNumberFromRequest(request);
		
		try {
			Film film = filmService.getByID(filmID, lang);
			request.setAttribute(RequestAndSessionAttributes.FILM, film);
			
			if (isAuthorized(session)) {
				if (!isAdmin(session)) {
					int userID = fetchUserIdFromSession(session);
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

	}

}
