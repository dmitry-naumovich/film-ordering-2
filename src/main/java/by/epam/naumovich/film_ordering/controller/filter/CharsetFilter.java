package by.epam.naumovich.film_ordering.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filters requests and responses by setting the character encoding to the value defined in web.xml (deploying descriptor) 
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class CharsetFilter implements Filter {

	private String encoding;
	private static final String CHAR_ENCODING = "characterEncoding";
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		arg0.setCharacterEncoding(encoding);
		arg1.setCharacterEncoding(encoding);
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		encoding = arg0.getInitParameter(CHAR_ENCODING);
	}
}
