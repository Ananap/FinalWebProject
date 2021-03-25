package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import by.panasenko.webproject.util.PagePath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserListCommand implements Command {
    static final Logger logger = LogManager.getLogger(UserListCommand.class);
    private static final String ATTRIBUTE_USER_LIST = "user";
    private UserServiceImpl service;

    public UserListCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<User> userList = new ArrayList<>();
        try{
            userList = service.findUserList();
        } catch (ServiceException e) {
            logger.error("Can't handle findUserList request at UserService",e);
        }
        req.setAttribute(ATTRIBUTE_USER_LIST, userList);
        return PagePath.MAIN_PAGE.getValue();
    }
}
