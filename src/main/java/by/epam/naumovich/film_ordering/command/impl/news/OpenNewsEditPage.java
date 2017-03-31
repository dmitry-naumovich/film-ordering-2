package by.epam.naumovich.film_ordering.command.impl.news;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Performs the command that gets news entity from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenNewsEditPage implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		int newsID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.NEWS_ID));
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null |
				!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.EDIT_NEWS_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_news&newsID=" + newsID).forward(request, response);
		}
		else {
			try {
				INewsService newsService = ServiceFactory.getInstance().getNewsService();
				News news = newsService.getNewsById(newsID);
				request.setAttribute(RequestAndSessionAttributes.NEWS, news);
				request.getRequestDispatcher(JavaServerPageNames.EDIT_NEWS_JSP_PAGE).forward(request, response);	
			} catch (ServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
