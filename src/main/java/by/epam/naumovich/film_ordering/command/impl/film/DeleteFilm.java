package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.SUCCESS_MESSAGE;

/**
 * Performs the command that reads film ID parameter from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class DeleteFilm implements Command {

    private final IFilmService filmService;

    public DeleteFilm(IFilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

        int filmID = fetchFilmIdFromRequest(request);

        if (!isAuthorized(session)) {
            request.setAttribute(ERROR_MESSAGE, ErrorMessages.DELETE_FILM_RESTRICTION);
            request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
        } else if (!isAdmin(session)) {
            request.setAttribute(ERROR_MESSAGE, ErrorMessages.DELETE_FILM_RESTRICTION);
            request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1")
                    .forward(request, response);
        } else {
            filmService.delete(filmID);

            log.debug(String.format(LogMessages.FILM_DELETED, filmID));
            request.setAttribute(SUCCESS_MESSAGE, SuccessMessages.FILM_DELETED);
            request.getRequestDispatcher("/Controller?command=open_all_films&pageNum=1").forward(request, response);
        }

    }

}
