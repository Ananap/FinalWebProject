package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import by.panasenko.webproject.util.PagePath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserByNameCommand implements Command {
    static final Logger logger = LogManager.getLogger(UserByNameCommand.class);
    private static final String NAME_VALUE = "name";
    private static final String ATTRIBUTE_USER = "user";
    private UserServiceImpl service;

    public UserByNameCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) {
        String nameValue = req.getParameter(NAME_VALUE);
        User user = null;
        try {
            user = service.findUserByName(nameValue);
        } catch (ServiceException e) {
            logger.error("Can't handle findUserByName request at UserService", e);
        }
        req.setAttribute(ATTRIBUTE_USER, user);
        return PagePath.MAIN_PAGE.getValue();
    }
}
