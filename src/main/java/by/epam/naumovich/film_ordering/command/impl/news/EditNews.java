package by.epam.naumovich.film_ordering.command.impl.news;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.INewsFileUploadService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.EditNewsServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that reads updated news parameters from the JSP and sends them to the relevant service class.
 * Uploads updated images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class EditNews implements Command {

	private final INewsFileUploadService fileUploadService;

	public EditNews(INewsFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);

		int newsId = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.NEWS_ID));
		if (!isAuthorized(session) || !isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.EDIT_NEWS_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_news&newsId=" + newsId)
					.forward(request, response);
		}
		else {
			try {
				fileUploadService.storeFilesAndUpdateNews(newsId, request, session);
				
				log.debug(String.format(LogMessages.NEWS_UPDATED, newsId));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.NEWS_EDITED);
				request.getRequestDispatcher("/Controller?command=open_single_news&newsId=" + newsId)
                        .forward(request, response);
			} catch (EditNewsServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_news_edit_page&newsId=" + newsId)
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
