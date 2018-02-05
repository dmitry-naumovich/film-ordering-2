package by.epam.naumovich.film_ordering.command.impl.review;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets all reviews from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenAllReviews implements Command {

    private final IFilmService filmService;
    private final IReviewService reviewService;
    private final IUserService userService;

    public OpenAllReviews(IFilmService filmService, IReviewService reviewService, IUserService userService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);
		String lang = fetchLanguageFromSession(session);
		int pageNum = fetchPageNumberFromRequest(request);
		
		try {
			List<Review> reviews = reviewService.getAllPart(pageNum);
			
			List<String> reviewLogins = new ArrayList<>();
			List<String> reviewFilmNames = new ArrayList<>();
			for (Review r : reviews) {
				reviewLogins.add(userService.getLoginByID(r.getAuthor()));
				reviewFilmNames.add(filmService.getByID(r.getFilmId(), lang).getName());
			}

			long totalPageAmount = reviewService.countPages();
			request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
			request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
			
			request.setAttribute(RequestAndSessionAttributes.REVIEW_VIEW_TYPE, RequestAndSessionAttributes.VIEW_TYPE_ALL);
			request.setAttribute(RequestAndSessionAttributes.REVIEWS, reviews);
			request.setAttribute(RequestAndSessionAttributes.LOGINS, reviewLogins);
			request.setAttribute(RequestAndSessionAttributes.FILM_NAMES, reviewFilmNames);
			request.getRequestDispatcher(JavaServerPageNames.REVIEWS_PAGE).forward(request, response);
			
		} catch (GetReviewServiceException e) {
			log.error(String.format(EXCEPTION_IN_COMMAND,
					e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.REVIEWS_PAGE).forward(request, response);
		}
	}

}
