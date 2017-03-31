package by.epam.naumovich.film_ordering.command.impl.review;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.review.GetReviewServiceException;

/**
 * Performs the command that forwards request and response from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenNewReviewPage implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);

		String lang = null;
		try {
			lang = session.getAttribute(RequestAndSessionAttributes.LANGUAGE).toString();
		} catch (NullPointerException e) {
			lang = RequestAndSessionAttributes.ENG_LANG;
		}
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_REVIEWING);
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
		}
		else {
			int filmID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID));
			int userID = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
			IFilmService filmService = ServiceFactory.getInstance().getFilmService();
			IReviewService reviewService = ServiceFactory.getInstance().getReviewService();
			Film film = null;
			try {
				film = filmService.getFilmByID(filmID, lang);
				reviewService.getReviewByUserAndFilmId(userID, filmID);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.REVIEW_AMOUNT_RESTRICTION);
				request.getRequestDispatcher("/Controller?command=open_single_review&userID=" + userID + "&filmID=" + filmID).forward(request, response);;
			} catch (GetFilmServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			} catch (GetReviewServiceException e) {
				request.setAttribute(RequestAndSessionAttributes.FILM, film);
				request.getRequestDispatcher(JavaServerPageNames.NEW_REVIEW_PAGE).forward(request, response);
			} catch (ServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
			
			
		}
		
		
	}

}
