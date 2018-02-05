package by.epam.naumovich.film_ordering.command.impl.news;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.GetNewsServiceException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that gets a single news from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenSingleNews implements Command {

	private final INewsService newsService;

	public OpenSingleNews(INewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {
		setPrevQueryAttributeToSession(request, session, log);

        int newsID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.NEWS_ID));

        try {
			News news = newsService.getById(newsID);
			request.setAttribute(RequestAndSessionAttributes.NEWS, news);
			request.getRequestDispatcher(JavaServerPageNames.SINGLE_NEWS_PAGE).forward(request, response);
		} catch (GetNewsServiceException e) {
            log.error(String.format(EXCEPTION_IN_COMMAND,
                    e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(JavaServerPageNames.SINGLE_NEWS_PAGE).forward(request, response);
        }
	}

}
