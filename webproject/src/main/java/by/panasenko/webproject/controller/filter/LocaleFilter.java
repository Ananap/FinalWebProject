package by.panasenko.webproject.controller.filter;

import by.panasenko.webproject.command.RequestAttribute;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = {"/Controller"})
public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        String queryString = httpRequest.getQueryString();
        String prevRequest = RequestAttribute.CONTROLLER_URL + queryString;
        if (session.getAttribute(RequestAttribute.LOCALE) == null) {
            session.setAttribute(RequestAttribute.LOCALE, Locale.getDefault());
        }
        if (queryString != null) {
            session.setAttribute(RequestAttribute.PREV_REQUEST, prevRequest);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
