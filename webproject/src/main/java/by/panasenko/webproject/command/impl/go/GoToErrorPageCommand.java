package by.panasenko.webproject.command.impl.go;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class GoToErrorPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        Throwable throwable = (Throwable) req.getAttribute(RequestAttribute.EXCEPTION_CLASS);
        if (throwable != null && req.getAttribute(RequestAttribute.EXCEPTION_CLASS) != null) {
            req.setAttribute(RequestAttribute.EXCEPTION, throwable);
        }
        return new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
    }
}
