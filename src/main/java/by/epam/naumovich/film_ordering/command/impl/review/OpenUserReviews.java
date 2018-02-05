package by.epam.naumovich.film_ordering.command.impl.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;

/**
 * Performs the command that gets all concrete user reviews from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenUserReviews implements Command {

    private final IFilmService filmService;
    private final IReviewService reviewService;

    public OpenUserReviews(IFilmService filmService, IReviewService reviewService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);

		String lang = fetchLanguageFromSession(session);
		
		int pageNum = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.PAGE_NUM));
		
		if (request.getParameter(RequestAndSessionAttributes.USER_ID).equals(RequestAndSessionAttributes.EMPTY_STRING)) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_YOUR_REVIEWS);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
			int userID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.USER_ID));

			try {
				List<Review> reviews = reviewService.getAllPartByUserId(userID, pageNum);
				
				List<String> reviewFilmNames = new ArrayList<>();
				for (Review r : reviews) {
					reviewFilmNames.add(filmService.getNameByID(r.getFilmId(), lang));
				}
				
				int totalPageAmount = reviewService.countByUserId(userID);
				request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
				request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
				
				request.setAttribute(RequestAndSessionAttributes.REVIEW_VIEW_TYPE, RequestAndSessionAttributes.VIEW_TYPE_USER);
				request.setAttribute(RequestAndSessionAttributes.USER_ID, userID);
				request.setAttribute(RequestAndSessionAttributes.REVIEWS, reviews);
				request.setAttribute(RequestAndSessionAttributes.FILM_NAMES, reviewFilmNames);
				request.getRequestDispatcher(JavaServerPageNames.REVIEWS_PAGE).forward(request, response);
			} catch (GetReviewServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
			} catch (ServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
		
	}

}
