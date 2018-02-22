package by.epam.naumovich.film_ordering.command.impl.film;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IFilmFileUploadService;
import by.epam.naumovich.film_ordering.service.exception.film.EditFilmServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that reads updated film parameters from the JSP and sends them to the relevant service class.
 * Uploads updated images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class EditFilm implements Command {

	private final IFilmFileUploadService fileUploadService;

	public EditFilm(IFilmFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {

		int filmId = fetchFilmIdFromRequest(request);
		if (!isAuthorized(session) || !isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.EDIT_FILM_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_film&filmId=" + filmId + "&pageNum=1")
                    .forward(request, response);
		}
		else {
			try {
				fileUploadService.storeFilesAndUpdateFilm(filmId, request, session);

				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.FILM_EDITED);
				request.getRequestDispatcher("/Controller?command=open_single_film&filmId=" + filmId + "&pageNum=1")
                        .forward(request, response);
			} catch (EditFilmServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_film_edit_page&filmId=" + filmId)
                        .forward(request, response);
			} catch (Exception e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
