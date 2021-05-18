package by.panasenko.webproject.controller;

import by.panasenko.webproject.controller.command.Command;
import by.panasenko.webproject.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@MultipartConfig
public class Controller extends HttpServlet {
    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();
    public static final String ATTRIBUTE_COMMAND = "command";
    private static final String ATTRIBUTE_LOCALE = "locale";
    private static final String ATTRIBUTE_PREV_REQUEST = "prev_request";
    private static final String CONTROLLER_URL = "Controller?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HttpSession SESSION = request.getSession();
        final String COMMAND_NAME = request.getParameter(ATTRIBUTE_COMMAND);
        final Command COMMAND = COMMAND_PROVIDER.getCommand(COMMAND_NAME);
        final String QUERY_STRING = request.getQueryString();
        final String PREV_REQUEST = CONTROLLER_URL + QUERY_STRING;

        if (SESSION.getAttribute(ATTRIBUTE_LOCALE) == null) {
            SESSION.setAttribute(ATTRIBUTE_LOCALE, Locale.getDefault());
        }

        if (COMMAND == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            COMMAND.execute(request, response);

            if (QUERY_STRING != null) {
                SESSION.setAttribute(ATTRIBUTE_PREV_REQUEST, PREV_REQUEST);
            }
        }
    }
}
