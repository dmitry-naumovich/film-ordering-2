package by.epam.naumovich.film_ordering.command.impl.review;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that forwards request and response from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenNewReviewPage implements Command {

	private final IFilmService filmService;
	private final IReviewService reviewService;

	public OpenNewReviewPage(IFilmService filmService, IReviewService reviewService) {
		this.filmService = filmService;
		this.reviewService = reviewService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);
		String lang = fetchLanguageFromSession(session);
		
		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_REVIEWING);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
			int filmID = fetchFilmIdFromRequest(request);
			int userID = fetchUserIdFromSession(session);
			Film film = null;
			try {
				film = filmService.getByID(filmID, lang);
				reviewService.getByUserAndFilmId(userID, filmID);

				request.setAttribute(ERROR_MESSAGE, ErrorMessages.REVIEW_AMOUNT_RESTRICTION);
				request.getRequestDispatcher("/Controller?command=open_single_review&userID=" + userID + "&filmID=" + filmID).forward(request, response);
			} catch (GetFilmServiceException e) {
				log.error(String.format(EXCEPTION_IN_COMMAND,
						e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			} catch (GetReviewServiceException e) {
				request.setAttribute(RequestAndSessionAttributes.FILM, film);
				request.getRequestDispatcher(JavaServerPageNames.NEW_REVIEW_PAGE).forward(request, response);
			}
		}
	}
}
