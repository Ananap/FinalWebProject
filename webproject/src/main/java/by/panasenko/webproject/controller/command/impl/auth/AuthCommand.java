package by.panasenko.webproject.controller.command.impl.auth;

import by.panasenko.webproject.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AuthCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_LOG_IN_TO_CONTINUE_LOCALE = "sign_in_to_continue";

    @Override
    public final void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkAuthAndProcess(req, resp);
    }

    private void checkAuthAndProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute(ATTRIBUTE_USER) == null) {
            req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_LOG_IN_TO_CONTINUE_LOCALE);
            // todo sign in
        } else {
            process(req, resp);
        }
    }

    protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
