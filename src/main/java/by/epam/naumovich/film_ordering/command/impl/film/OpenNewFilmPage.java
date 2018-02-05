package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets genres and countries arrays from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenNewFilmPage implements Command {

	private final IFilmService filmService;

	public OpenNewFilmPage(IFilmService filmService) {
		this.filmService = filmService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

        setPrevQueryAttributeToSession(request, session, log);
        String lang = fetchLanguageFromSession(session);

		if (!isAuthorized(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_FILM_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else if (!isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_FILM_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_all_films&pageNum=1").forward(request, response);
		}
		else {
            String[] genres = filmService.getAvailableGenres(lang);
            String[] countries = filmService.getAvailableCountries(lang);
            request.setAttribute(RequestAndSessionAttributes.AVAILABLE_GENRES, genres);
            request.setAttribute(RequestAndSessionAttributes.AVAILABLE_COUNTRIES, countries);
            request.getRequestDispatcher(JavaServerPageNames.FILM_ADDING_PAGE).forward(request, response);
		}

	}

}
