package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
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
    private static final String ERROR_MESSAGE = "Error at PersonalEditCommand";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_FIRST_NAME = "firstName";
    private static final String ATTRIBUTE_LAST_NAME = "lastName";
    private static final String ATTRIBUTE_ADDRESS = "address";
    private static final String ATTRIBUTE_USERNAME = "username";
    private static final String ATTRIBUTE_PHONE = "phone";
    private static final String ATTRIBUTE_CURRENT_PASSWORD = "currentPassword";
    private static final String ATTRIBUTE_NEW_PASSWORD = "newPassword";
    private static final String ATTRIBUTE_CONFIRM_PASSWORD = "confirmPassword";
    private static final String ATTRIBUTE_SUCCESS_EDIT = "updateUserInfo";
    private static final String ATTRIBUTE_WRONG_CONFIRMATION = "passwordNotEquals";
    private static final String ATTRIBUTE_WRONG_PASSWORD = "currentPasswordNotEquals";

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        final String firstName = req.getParameter(ATTRIBUTE_FIRST_NAME);
        final String lastName = req.getParameter(ATTRIBUTE_LAST_NAME);
        final String address = req.getParameter(ATTRIBUTE_ADDRESS);
        final String username = req.getParameter(ATTRIBUTE_USERNAME);
        final String phone = req.getParameter(ATTRIBUTE_PHONE);
        final String currentPassword = req.getParameter(ATTRIBUTE_CURRENT_PASSWORD);
        final String newPassword = req.getParameter(ATTRIBUTE_NEW_PASSWORD);
        final String confirmPassword = req.getParameter(ATTRIBUTE_CONFIRM_PASSWORD);

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
                    req.setAttribute(ATTRIBUTE_WRONG_PASSWORD, true);
                    break;
                }
                case WRONG_CONFIRMATION: {
                    req.setAttribute(ATTRIBUTE_WRONG_CONFIRMATION, true);
                    break;
                }
                case SUCCESS: {
                    req.setAttribute(ATTRIBUTE_SUCCESS_EDIT, true);
                    req.getSession().setAttribute(ATTRIBUTE_USER, user);
                    break;
                }
            }
            router = new Router(PagePath.GO_TO_PROFILE_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
