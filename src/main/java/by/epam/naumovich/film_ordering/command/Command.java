package by.epam.naumovich.film_ordering.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines methods for overriding in all Command implementations which should perform a command with help of which
 * the Controller sends and received data to/from the service layer
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@FunctionalInterface
public interface Command {
	/**
	 * Proceeds the request, sends data to and receive it from the service layer and then forwards
	 * both request and response to the JSP page or to the Controller servlet which will execute another command
	 * 
	 * @param request servlet request
	 * @param response servlet response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
