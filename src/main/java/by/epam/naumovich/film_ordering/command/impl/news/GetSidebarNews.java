package by.epam.naumovich.film_ordering.command.impl.news;

import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that gets four last added news from the service layer and passes it to the relevant JSP.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class GetSidebarNews implements Command {

    private final INewsService newsService;

    public GetSidebarNews(INewsService newsService) {
        this.newsService = newsService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException, ServiceException {
        List<News> newsSet = newsService.getFourLastNews();
        //request.setAttribute(RequestAndSessionAttributes.SIDEBAR_NEWS, newsSet);
        session.setAttribute(RequestAndSessionAttributes.SIDEBAR_NEWS, newsSet);
	}
}
