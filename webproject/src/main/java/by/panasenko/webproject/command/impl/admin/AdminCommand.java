package by.panasenko.webproject.command.impl.admin;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.entity.Role;
import by.panasenko.webproject.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AdminCommand implements Command {

    @Override
    public final Router execute(HttpServletRequest req) {
        return checkAuthAndProcess(req);
    }

    private Router checkAuthAndProcess(HttpServletRequest req) {
        Router router;
        User user = (User) req.getSession().getAttribute(RequestAttribute.USER);
        if (user != null) {
            if (user.getRole() == Role.ADMIN) {
                return process(req);
            } else {
                router = new Router(PagePath.ERROR_404_PAGE, Router.RouterType.REDIRECT);
            }
        } else {
            req.setAttribute(RequestAttribute.MESSAGE, true);
            router = new Router(PagePath.GO_TO_LOGIN_PAGE, Router.RouterType.FORWARD);
        }
        return router;
    }

    protected abstract Router process(HttpServletRequest req);
}
