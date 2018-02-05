package by.epam.naumovich.film_ordering.command.impl.navigation;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
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
 * Performs the command that gets genres and countries arrays from the service layer and then forwards request and response to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenWidenedSearchPage implements Command {

	private final IFilmService filmService;

	public OpenWidenedSearchPage(IFilmService filmService) {
		this.filmService = filmService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

        setPrevQueryAttributeToSession(request, session, log);
		String lang = fetchLanguageFromSession(session);
		
        String[] genres = filmService.getAvailableGenres(lang);
        String[] countries = filmService.getAvailableCountries(lang);
        request.setAttribute(RequestAndSessionAttributes.AVAILABLE_GENRES, genres);
        request.setAttribute(RequestAndSessionAttributes.AVAILABLE_COUNTRIES, countries);
        request.getRequestDispatcher(JavaServerPageNames.WIDEN_SEARCH_PAGE).forward(request, response);
	}
}
