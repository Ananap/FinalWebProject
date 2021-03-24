package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.controller.Servlet;
import by.panasenko.webproject.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SortUserCommand implements Command {
    static final Logger logger = LogManager.getLogger(Servlet.class);
    private static final String MAIN_PAGE = "/pages/main.jsp";
    private UserServiceImpl service;

    public SortUserCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<User> userList = service.sortByName();
        req.setAttribute("userList", userList);
        logger.info("Sort by name");
        return MAIN_PAGE;
    }
}
