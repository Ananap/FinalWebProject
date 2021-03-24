package by.panasenko.webproject.controller;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.CommandProvider;
import by.panasenko.webproject.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import by.panasenko.webproject.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "by/panasenko/webproject/controller", urlPatterns = "/by/panasenko/webproject/controller")
public class Servlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(Servlet.class);
    public static final String COMMAND = "by/panasenko/webproject/command";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserServiceImpl userService = new UserServiceImpl();
        String page;
        String commandName = request.getParameter(COMMAND);
        if (commandName == null || commandName.isEmpty()) {
            List<User> userList = userService.getUserList();
            request.setAttribute("userList", userList);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/pages/main.jsp");
            dispatcher.forward(request, response);
        } else {
            logger.info("Success by.panasenko.webproject.command");
            Command command = CommandProvider.getInstance().getCommand(commandName);
            page = command.execute(request);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}
