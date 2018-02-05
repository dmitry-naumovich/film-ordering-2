package by.epam.naumovich.film_ordering.command.impl.review;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.REVIEW_DELETED;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.SUCCESS_MESSAGE;

/**
 * Performs the command that reads review user and film IDs parameters from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class DeleteReview implements Command {

	private final IReviewService reviewService;

	public DeleteReview(IReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		int userID = Integer.valueOf(request.getParameter(RequestAndSessionAttributes.USER_ID));
		int filmID = Integer.valueOf(request.getParameter(RequestAndSessionAttributes.FILM_ID));
		 
		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.DELETE_REVIEW_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else if (!isAdmin(session) && userID != fetchUserIdFromSession(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.DELETE_REVIEW_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_review&userID=" + userID + "&filmID=" + filmID).forward(request, response);
		}
		else {
			reviewService.delete(userID, filmID);

			log.debug(String.format(REVIEW_DELETED, userID, filmID));
			request.setAttribute(SUCCESS_MESSAGE, SuccessMessages.REVIEW_DELETED);
			request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1")
					.forward(request, response);
		}
	}

}
