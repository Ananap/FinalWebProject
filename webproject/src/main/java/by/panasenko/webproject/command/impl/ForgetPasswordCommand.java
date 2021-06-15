package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.*;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgetPasswordCommand implements Command {
    private static final Logger logger = Logger.getLogger(ForgetPasswordCommand.class);

    @Override
    public Router execute(HttpServletRequest req) {
        Router router;
        String email = req.getParameter(RequestParameter.RECOVER_EMAIL);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        try {
            if (userService.forgetPassword(email)) {
                req.setAttribute(RequestAttribute.EMAIL_SENT, true);
            } else {
                req.setAttribute(RequestAttribute.EMAIL_NOT_EXISTS, true);
            }
            router = new Router(PagePath.GO_TO_LOGIN_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at ForgetPasswordCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
