package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgetPasswordCommand implements Command {
    private static final Logger logger = Logger.getLogger(ForgetPasswordCommand.class);
    private static final String ERROR_MESSAGE = "Error at ForgetPasswordCommand";
    private static final String ATTRIBUTE_EMAIL = "recoverEmail";
    private static final String ATTRIBUTE_EMAIL_NOT_EXISTS = "emailNotExists";
    private static final String ATTRIBUTE_EMAIL_SENT = "emailSent";
    private static final String ATTRIBUTE_EXCEPTION = "exception";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse res) {
        Router router;
        String email = req.getParameter(ATTRIBUTE_EMAIL);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        try {
            if (userService.forgetPassword(email)) {
                req.setAttribute(ATTRIBUTE_EMAIL_SENT, true);
            } else {
                req.setAttribute(ATTRIBUTE_EMAIL_NOT_EXISTS, true);
            }
            router = new Router(PagePath.GO_TO_LOGIN_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
