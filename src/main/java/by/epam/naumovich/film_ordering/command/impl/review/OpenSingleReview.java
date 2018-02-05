package by.epam.naumovich.film_ordering.command.impl.review;

import by.epam.naumovich.film_ordering.bean.Review;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
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
 * Performs the command that gets a single review from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenSingleReview implements Command {

    private final IFilmService filmService;
    private final IReviewService reviewService;
    private final IUserService userService;

    public OpenSingleReview(IFilmService filmService, IReviewService reviewService, IUserService userService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);

		String lang = fetchLanguageFromSession(session);
		int userID = fetchUserIdFromRequest(request);
		int filmID = fetchFilmIdFromRequest(request);
		
		try {
			Review rev = reviewService.getByUserAndFilmId(userID, filmID);
			String userLogin = userService.getLoginByID(userID);
			String filmName = filmService.getByID(filmID, lang).getName();
			
			request.setAttribute(RequestAndSessionAttributes.REVIEW, rev);
			request.setAttribute(RequestAndSessionAttributes.LOGIN, userLogin);
			request.setAttribute(RequestAndSessionAttributes.FILM_NAME, filmName);

			request.getRequestDispatcher(JavaServerPageNames.SINGLE_REVIEW_PAGE).forward(request, response);
		} catch (GetReviewServiceException e) {
			log.error(String.format(EXCEPTION_IN_COMMAND,
                    e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}

	}

}
