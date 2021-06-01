package by.panasenko.webproject.controller.command.impl.go;

import by.panasenko.webproject.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToErrorPageCommand implements Command {

    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_EXCEPTION_CLASS = "javax.servlet.error.exception";
    private static final String ERROR_PAGE_URL = "error.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute(ATTRIBUTE_EXCEPTION_CLASS);

        if (throwable != null && req.getAttribute(ATTRIBUTE_EXCEPTION) != null) {
            req.setAttribute(ATTRIBUTE_EXCEPTION, throwable);
        }

        req.getRequestDispatcher(ERROR_PAGE_URL).forward(req, resp);
    }
}
