package by.epam.naumovich.film_ordering.command.impl.review;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.AddReviewServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.REVIEW_CREATED;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.SUCCESS_MESSAGE;

/**
 * Performs the command that reads new review parameters from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class AddReview implements Command {

	private final IReviewService reviewService;

	public AddReview(IReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_REVIEWING);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
			int userID = fetchUserIdFromRequest(request);
			int filmID = fetchFilmIdFromRequest(request);
			String mark = request.getParameter(RequestAndSessionAttributes.REVIEW_MARK);
			String type = request.getParameter(RequestAndSessionAttributes.REVIEW_TYPE);
			String text = request.getParameter(RequestAndSessionAttributes.REVIEW_TEXT);
			
			try {
				reviewService.create(userID, filmID, mark, type, text);

				log.debug(String.format(REVIEW_CREATED, userID, filmID));
				request.setAttribute(SUCCESS_MESSAGE, SuccessMessages.REVIEW_ADDED);
				request.getRequestDispatcher("/Controller?command=open_single_review&userID=" + userID + "&filmID=" + filmID).forward(request, response);

			} catch (AddReviewServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
						e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_new_review_page&filmID=" + filmID).forward(request, response);
			}
		}
	}
}
