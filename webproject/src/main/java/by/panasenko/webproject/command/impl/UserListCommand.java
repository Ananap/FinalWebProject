package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import by.panasenko.webproject.util.PagePath;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserListCommand implements Command {
    private static final String ATTRIBUTE_USER_LIST = "user";
    private UserServiceImpl service;

    public UserListCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) throws ServiceException {
        List<User> userList = service.findUserList();
        req.setAttribute(ATTRIBUTE_USER_LIST, userList);
        return PagePath.MAIN_PAGE.getValue();
    }
}
