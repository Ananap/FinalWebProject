package by.panasenko.webproject.controller;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.CommandProvider;
import by.panasenko.webproject.command.Router;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
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
    private static Logger logger = LogManager.getLogger(Controller.class);
    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();
    public static final String ATTRIBUTE_COMMAND = "command";
    private static final String ATTRIBUTE_LOCALE = "locale";
    private static final String ATTRIBUTE_PREV_REQUEST = "prev_request";
    private static final String ERROR_PAGE_URL = "error.jsp";
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
        HttpSession session = request.getSession();
        String commandName = request.getParameter(ATTRIBUTE_COMMAND);
        Command command = COMMAND_PROVIDER.getCommand(commandName);
        String queryString = request.getQueryString();
        String prevRequest = CONTROLLER_URL + queryString;
        if (session.getAttribute(ATTRIBUTE_LOCALE) == null) {
            session.setAttribute(ATTRIBUTE_LOCALE, Locale.getDefault());
        }
        Router router = command.execute(request, response);
        if (queryString != null) {
            session.setAttribute(ATTRIBUTE_PREV_REQUEST, prevRequest);
        }
        switch (router.getRouterType()) {
            case REDIRECT:
                response.sendRedirect(router.getPagePath());
                break;
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
                break;
            default:
                logger.error("incorrect route type " + router.getRouterType());
                response.sendRedirect(ERROR_PAGE_URL);
                break;
        }
    }
}
