package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.controller.Servlet;
import by.panasenko.webproject.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToOriginalCommand implements Command {
    static final Logger logger = LogManager.getLogger(Servlet.class);
    private static final String MAIN_PAGE = "/pages/main.jsp";
    private UserServiceImpl service;

    public GoToOriginalCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<User> userList = service.getUserList();
        req.setAttribute("userList", userList);
        logger.info("Go to original table");
        return MAIN_PAGE;
    }
}
