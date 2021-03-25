package by.panasenko.webproject.controller;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.CommandProvider;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    static final Logger logger = LogManager.getLogger(Controller.class);
    public static final String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        String commandName = request.getParameter(COMMAND);
        Command command = CommandProvider.getInstance().getCommand(commandName);
        page = command.execute(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
