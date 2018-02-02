package by.epam.naumovich.film_ordering.command.impl.navigation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

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
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);

		String lang = fetchLanguageFromSession(session);
		
		try {
			String[] genres = filmService.getAvailableGenres(lang);
			String[] countries = filmService.getAvailableCountries(lang);
			request.setAttribute(RequestAndSessionAttributes.AVAILABLE_GENRES, genres);
			request.setAttribute(RequestAndSessionAttributes.AVAILABLE_COUNTRIES, countries);
			request.getRequestDispatcher(JavaServerPageNames.WIDEN_SEARCH_PAGE).forward(request, response);
		} catch (ServiceException e) {
			log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}
	}
}
