package by.epam.naumovich.film_ordering.command.impl.news;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.GetNewsServiceException;

/**
 * Performs the command that gets all news from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenAllNews implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query = QueryUtil.createHttpQueryString(request);
		request.getSession(true).setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		int pageNum = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.PAGE_NUM));
		
		try {
			INewsService newsService = ServiceFactory.getInstance().getNewsService();
			Set<News> news = newsService.getAllNewsPart(pageNum);
			
			int totalPageAmount = newsService.getNumberOfAllNewsPages();
			request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
			request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
			
			request.setAttribute(RequestAndSessionAttributes.NEWS, news);
			request.getRequestDispatcher(JavaServerPageNames.NEWS_JSP_PAGE).forward(request, response);
		} catch (GetNewsServiceException e) {
			LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.NEWS_JSP_PAGE).forward(request, response);
		} catch (ServiceException e) {
			LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}
	}
}
