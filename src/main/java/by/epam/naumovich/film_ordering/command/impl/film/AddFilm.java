package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IFilmFileUploadService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.AddFilmServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that reads new film parameters from the JSP and sends them to the relevant service class.
 * Uploads images to the required directories.
 * Checks the access rights of the user who is performing this action.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class AddFilm implements Command {

    private final IFilmFileUploadService fileUploadService;

    public AddFilm(IFilmFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {

        if (!isAuthorized(session)) {
            request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_FILM_RESTRICTION);
            request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
        } else if (!isAdmin(session)) {
            request.setAttribute(ERROR_MESSAGE, ErrorMessages.ADD_FILM_RESTRICTION);
            request.getRequestDispatcher("/Controller?command=open_all_films&pageNum=1").forward(request, response);
        } else {
            try {
                int filmID = fileUploadService.storeFilesAndAddFilm(request);

                request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.FILM_ADDED);
                request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1")
                        .forward(request, response);
            } catch (AddFilmServiceException e) {
                log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
                request.getRequestDispatcher(JavaServerPageNames.FILM_ADDING_PAGE).forward(request, response);
            } catch (Exception e) {
                log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
                request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
            }
        }
    }
}
