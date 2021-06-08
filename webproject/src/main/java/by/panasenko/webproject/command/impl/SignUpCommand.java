package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
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

    private static final String ERROR_MESSAGE = "Error at SignUpServlet";
    private static final String ATTRIBUTE_USERNAME = "username";
    private static final String ATTRIBUTE_FIRSTNAME = "firstName";
    private static final String ATTRIBUTE_LASTNAME = "lastName";
    private static final String ATTRIBUTE_ADDRESS = "address";
    private static final String ATTRIBUTE_EMAIL = "email";
    private static final String ATTRIBUTE_PHONE = "phone";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_DUPLICATE_EMAIL = "duplicateEmail";
    private static final String ATTRIBUTE_EMAIL_SENT = "emailSent";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse res) {
        Router router;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        final String username = req.getParameter(ATTRIBUTE_USERNAME);
        final String firstName = req.getParameter(ATTRIBUTE_FIRSTNAME);
        final String lastName = req.getParameter(ATTRIBUTE_LASTNAME);
        final String address = req.getParameter(ATTRIBUTE_ADDRESS);
        final String email = req.getParameter(ATTRIBUTE_EMAIL);
        final String phone = req.getParameter(ATTRIBUTE_PHONE);
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
                    req.setAttribute(ATTRIBUTE_DUPLICATE_EMAIL, true);
                    break;
                case SUCCESS:
                    MailSender.sendEmail(email, MailSender.messageEmailUser(username, password));
                    req.setAttribute(ATTRIBUTE_EMAIL_SENT, true);
                    break;
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
