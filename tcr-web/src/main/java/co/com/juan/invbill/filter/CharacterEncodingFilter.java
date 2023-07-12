package co.com.juan.invbill.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 * @author Juan Felipe
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig config) throws ServletException {
        context = config.getServletContext();
        this.context.log("CharacterEncodingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.context.log("CharacterEncodingFilter destroyed");
    }

}
