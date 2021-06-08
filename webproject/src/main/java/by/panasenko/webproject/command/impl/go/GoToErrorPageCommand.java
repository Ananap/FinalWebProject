package by.panasenko.webproject.command.impl.go;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToErrorPageCommand implements Command {
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_EXCEPTION_CLASS = "javax.servlet.error.exception";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Throwable throwable = (Throwable) req.getAttribute(ATTRIBUTE_EXCEPTION_CLASS);
        if (throwable != null && req.getAttribute(ATTRIBUTE_EXCEPTION) != null) {
            req.setAttribute(ATTRIBUTE_EXCEPTION, throwable);
        }
        return new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
    }
}
