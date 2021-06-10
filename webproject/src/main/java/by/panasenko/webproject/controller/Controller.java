package by.panasenko.webproject.controller;

import by.panasenko.webproject.command.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger(Controller.class);
    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        Command command = COMMAND_PROVIDER.getCommand(commandName);
        Router router = command.execute(request, response);
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
                response.sendRedirect(PagePath.ERROR_PAGE);
        }
    }
}
