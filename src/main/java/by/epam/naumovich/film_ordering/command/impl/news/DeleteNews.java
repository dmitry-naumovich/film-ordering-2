package by.epam.naumovich.film_ordering.command.impl.news;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that reads news ID parameter from the JSP and sends them to the relevant service class.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class DeleteNews implements Command {

	private final INewsService newsService;

	public DeleteNews(INewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		int newsId = Integer.valueOf(request.getParameter(RequestAndSessionAttributes.NEWS_ID));
		 
		if (!isAuthorized(session) || !isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.DELETE_NEWS_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_news&newsId=" + newsId)
					.forward(request, response);
		}
		else {
            newsService.delete(newsId);
            log.debug(String.format(LogMessages.NEWS_DELETED, newsId));
            request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.NEWS_DELETED);
            request.getRequestDispatcher("/Controller?command=open_all_news&pageNum=1").forward(request, response);
		}
	}
}
