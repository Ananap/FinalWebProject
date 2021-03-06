package by.panasenko.webproject.command.impl.auth;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AuthCommand implements Command {

    @Override
    public final Router execute(HttpServletRequest req) {
        return checkAuthAndProcess(req);
    }

    private Router checkAuthAndProcess(HttpServletRequest req) {
        if (req.getSession().getAttribute(RequestAttribute.USER) == null) {
            req.setAttribute(RequestAttribute.MESSAGE, true);
            Router router = new Router(PagePath.GO_TO_LOGIN_PAGE, RouterType.FORWARD);
            return router;
        } else {
            return process(req);
        }
    }

    protected abstract Router process(HttpServletRequest req);
}
