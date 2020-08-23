package co.com.juan.invbill.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * @author Juan Felipe
 * 
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

	@Override
	public void destroy() {
		// Called by the web container to indicate to a filter that it is being taken
		// out of service.
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// Called by the web container to indicate to a filter that it is being placed
		// into service.
	}
}
