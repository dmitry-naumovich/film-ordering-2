package by.epam.naumovich.film_ordering.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.stereotype.Component;

/**
 * Filters requests and responses by setting the character encoding to the value defined in web.xml (deploying descriptor) 
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Component
public class CharsetFilter implements Filter {

    private static final String CHAR_ENCODING = "characterEncoding";

    private String encoding;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter(CHAR_ENCODING);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
