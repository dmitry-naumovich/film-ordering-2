package by.epam.naumovich.film_ordering.command.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * Contains different command utilities which work with HttpServletRequest or HttpServletResponse objects.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public final class QueryUtil {

	private QueryUtil() {}
	
	/**
	 * Creates HTTP Query String based on the HTTP Servlet Request
	 * 
	 * @param request the request
	 * @return String query
	 */
	public static String createHttpQueryString(HttpServletRequest request) {
		StringBuilder queryBuilder = new StringBuilder(request.getRequestURL() + "?");
		Enumeration<String> params = request.getParameterNames();

		String key;
		String value;
		while (params.hasMoreElements()) {
			key = params.nextElement();
			value  = request.getParameter(key);
			queryBuilder.append("&").append(key).append("=").append(value);
		}
		
		return queryBuilder.toString();
	}

}
