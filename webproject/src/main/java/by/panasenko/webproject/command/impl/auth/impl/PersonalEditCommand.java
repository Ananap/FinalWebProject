package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.ResultCode;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersonalEditCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(PersonalEditCommand.class);

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final User user = (User) req.getSession().getAttribute(RequestAttribute.USER);
        final String firstName = req.getParameter(RequestParameter.FIRSTNAME);
        final String lastName = req.getParameter(RequestParameter.LASTNAME);
        final String address = req.getParameter(RequestParameter.ADDRESS);
        final String username = req.getParameter(RequestParameter.USERNAME);
        final String phone = req.getParameter(RequestParameter.PHONE);
        final String currentPassword = req.getParameter(RequestParameter.CURRENT_PASSWORD);
        final String newPassword = req.getParameter(RequestParameter.NEW_PASSWORD);
        final String confirmPassword = req.getParameter(RequestParameter.CONFIRM_PASSWORD);

        final SignInData signInData = new SignUpData();
        signInData.setEmail(user.getEmail());
        signInData.setPassword(currentPassword);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhone(phone);
        user.setPassword(currentPassword);

        try {
            ResultCode resultCode = userService.updateUser(signInData, user, newPassword, confirmPassword);
            switch (resultCode) {
                case WRONG_PASSWORD: {
                    req.setAttribute(RequestAttribute.WRONG_PASSWORD, true);
                    break;
                }
                case WRONG_CONFIRMATION: {
                    req.setAttribute(RequestAttribute.WRONG_CONFIRMATION, true);
                    break;
                }
                case SUCCESS: {
                    req.setAttribute(RequestAttribute.SUCCESS_EDIT, true);
                    req.getSession().setAttribute(RequestAttribute.USER, user);
                    break;
                }
            }
            router = new Router(PagePath.GO_TO_PROFILE_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at PersonalEditCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
