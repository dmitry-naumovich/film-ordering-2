package by.epam.naumovich.film_ordering.command.impl.news;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
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
 * Performs the command that gets news entity from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenNewsEditPage implements Command {

	private final INewsService newsService;

	public OpenNewsEditPage(INewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

        setPrevQueryAttributeToSession(request, session, log);
		
		int newsID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.NEWS_ID));
		if (!isAuthorized(session) || !isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.EDIT_NEWS_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_news&newsID=" + newsID)
					.forward(request, response);
		}
		else {
            News news = newsService.getById(newsID);
            request.setAttribute(RequestAndSessionAttributes.NEWS, news);
            request.getRequestDispatcher(JavaServerPageNames.EDIT_NEWS_JSP_PAGE).forward(request, response);
		}
	}
}
