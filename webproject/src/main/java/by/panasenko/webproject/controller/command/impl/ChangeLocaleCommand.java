package by.panasenko.webproject.controller.command.impl;

import by.panasenko.webproject.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLocaleCommand implements Command {

    private static final String PARAMETER_LOCALE = "locale";
    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "prev_request";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession SESSION = req.getSession();
        final String LOCALE = req.getParameter(PARAMETER_LOCALE);
        final String PREVIOUS_REQUEST = (String) SESSION.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST);

        SESSION.setAttribute(PARAMETER_LOCALE, LOCALE);
        resp.sendRedirect(PREVIOUS_REQUEST);
    }
}
