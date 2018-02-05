package by.epam.naumovich.film_ordering.controller;

import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * HttpServlet implementation class Controller which receives incoming HttpServletRequest, serves it by defining the necessary command and
 * calls its method 'execute' and returns back to the client the HTTPServletResponse
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
@org.springframework.stereotype.Controller
@RequestMapping("/Controller")
public class Controller {
	
	private static final String COMMAND = "command";
	private CommandHelper commandHelper;

	@Autowired
    public Controller(CommandHelper commandHelper) {
        this.commandHelper = commandHelper;
    }

    /**
     * Processes GET requests, delegates the logic to post(..) method
     *
     * @param request HttpRequest from JSP page
     * @param response HttpResponse object from view
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		post(request, response);
	}

    /**
     * Takes the command name parameter from request, finds necessary Command implementation and calls its method 'execute'
     *
     * @param request HttpRequest from JSP page
     * @param response HttpResponse object from view
     * @throws ServletException
     * @throws IOException
     */
	@RequestMapping(method = RequestMethod.POST)
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        log.debug("Controller::post, commandName = {}, requestUrl = {}", commandName, request.getRequestURL());
        Command command = commandHelper.getCommand(commandName.toUpperCase());

	    try {
			command.execute(request, response, request.getSession(true));
		} catch (ServiceException e) {
            log.error(String.format(EXCEPTION_IN_COMMAND,
                    e.getClass().getSimpleName(), command.getClass().getSimpleName(), e.getMessage()), e);

            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		} catch (RuntimeException e) {
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}
	}
}
