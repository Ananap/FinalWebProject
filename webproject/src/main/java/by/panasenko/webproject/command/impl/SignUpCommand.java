package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.*;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.ResultCode;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.UserService;
import by.panasenko.webproject.util.MailSender;
import by.panasenko.webproject.util.PasswordEncryptor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {
    private static final Logger logger = Logger.getLogger(SignUpCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse res) {
        Router router;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        final String username = req.getParameter(RequestParameter.USERNAME);
        final String firstName = req.getParameter(RequestParameter.FIRSTNAME);
        final String lastName = req.getParameter(RequestParameter.LASTNAME);
        final String address = req.getParameter(RequestParameter.ADDRESS);
        final String email = req.getParameter(RequestParameter.EMAIL);
        final String phone = req.getParameter(RequestParameter.PHONE);
        final String password = PasswordEncryptor.generateRandomPassword();

        SignUpData signUpData = new SignUpData();

        signUpData.setUsername(username);
        signUpData.setPassword(password);
        signUpData.setFirstName(firstName);
        signUpData.setLastName(lastName);
        signUpData.setAddress(address);
        signUpData.setEmail(email);
        signUpData.setPhoneNumber(phone);
        try {
            ResultCode resultCode = userService.signUp(signUpData);
            switch (resultCode) {
                case ERROR_DUPLICATE_EMAIL:
                    req.setAttribute(RequestAttribute.DUPLICATE_EMAIL, true);
                    break;
                case SUCCESS:
                    MailSender.sendEmail(email, MailSender.messageEmailUser(username, password));
                    req.setAttribute(RequestAttribute.EMAIL_SENT, true);
                    break;
            }
            router = new Router(PagePath.GO_TO_LOGIN_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at SignUpServlet", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
