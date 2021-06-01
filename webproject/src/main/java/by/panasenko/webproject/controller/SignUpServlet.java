package by.panasenko.webproject.controller;

import by.panasenko.webproject.controller.command.CommandProvider;
import by.panasenko.webproject.dao.ResultCode;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.ServiceProvider;
import by.panasenko.webproject.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SignUpServlet.class);

    private static final String ERROR_MESSAGE = "Error at SignUpServlet";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USERNAME = "username";
    private static final String ATTRIBUTE_FIRSTNAME = "firstName";
    private static final String ATTRIBUTE_LASTNAME = "lastName";
    private static final String ATTRIBUTE_ADDRESS = "address";
    private static final String ATTRIBUTE_EMAIL = "email";
    private static final String ATTRIBUTE_PHONE = "phone";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_DUPLICATE_LOGIN_LOCALE = "login_already_taken";
    private static final String SIGN_UP_PAGE_URL = "sign_up.jsp";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        final String username = req.getParameter(ATTRIBUTE_USERNAME);
        final String firstName = req.getParameter(ATTRIBUTE_FIRSTNAME);
        final String lastName = req.getParameter(ATTRIBUTE_LASTNAME);
        final String address = req.getParameter(ATTRIBUTE_ADDRESS);
        final String email = req.getParameter(ATTRIBUTE_EMAIL);
        final String phone = req.getParameter(ATTRIBUTE_PHONE);
        final String password = userService.generateRandomPassword();

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
                case RESULT_ERROR_DUPLICATE_EMAIL:
                    req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_DUPLICATE_LOGIN_LOCALE);
                    req.getRequestDispatcher(SIGN_UP_PAGE_URL).forward(req, resp);
                    break;
                case RESULT_SUCCESS:
                    resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
                    break;
            }

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
