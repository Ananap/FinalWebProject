package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import by.panasenko.webproject.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class UserByNameCommand implements Command {
    private static final String NAME_VALUE = "name";
    private static final String ATTRIBUTE_USER = "user";
    private UserServiceImpl service;

    public UserByNameCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) throws ServiceException {
        String nameValue = req.getParameter(NAME_VALUE);
        User user = service.findUserByName(nameValue);
        req.setAttribute(ATTRIBUTE_USER, user);
        return PagePath.MAIN_PAGE.getValue();
    }
}
