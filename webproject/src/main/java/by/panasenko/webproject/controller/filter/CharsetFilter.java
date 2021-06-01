package by.panasenko.webproject.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private String encoding;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("characterEncoding");
        context = filterConfig.getServletContext();
        context.log("Parameter encoding = " + encoding);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);

        context.log("Encoding set: " + encoding);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
