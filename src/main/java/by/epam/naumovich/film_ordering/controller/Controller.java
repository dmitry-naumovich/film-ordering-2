package by.epam.naumovich.film_ordering.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;

/**
 * HttpServlet implementation class Controller which receives incoming HttpServletRequest, serves it by defining the necessary command and
 * calls its method 'execute' and returns back to the client the HTTPServletResponse
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String COMMAND = "command";
       
    /**
     * Initializes Controller servlet object
     * 
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Takes the command name parameter from request, finds necessary Command implementation and calls its method 'execute'
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String commandName = request.getParameter(COMMAND);
			Command command  = CommandHelper.getInstance().getCommand(commandName.toUpperCase());
			command.execute(request, response);
		} catch (RuntimeException e) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
			request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
		}
	}
}
