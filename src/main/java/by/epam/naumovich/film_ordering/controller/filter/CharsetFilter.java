package by.epam.naumovich.film_ordering.controller.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters requests and responses by setting the character characterEncoding
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Component
@WebFilter(filterName = "charsetFilter", urlPatterns = "/Controller",
        initParams = @WebInitParam(name = "characterEncoding", value = "UTF-8"))
public class CharsetFilter extends GenericFilterBean {

    private static final String CHAR_ENCODING = "characterEncoding";

    private String characterEncoding = "UTF-8"; //todo: try to fetch it from initParams

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(characterEncoding);
		response.setCharacterEncoding(characterEncoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
