package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        final HttpSession SESSION = req.getSession();
        final String LOCALE = req.getParameter(RequestParameter.LOCALE);
        final String PREVIOUS_REQUEST = (String) SESSION.getAttribute(RequestAttribute.PREV_REQUEST);
        SESSION.setAttribute(RequestAttribute.LOCALE, LOCALE);
        Router router = new Router(PREVIOUS_REQUEST, RouterType.REDIRECT);
        return router;
    }
}
