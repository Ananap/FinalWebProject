package by.panasenko.webproject.command.impl.auth.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToProfilePageCommand extends AuthCommand {

    @Override
    public Router process(HttpServletRequest req, HttpServletResponse resp) {
        // todo order info

        req.setAttribute(RequestAttribute.ACTIVE_EDIT, true);
        Router router = new Router(PagePath.PROFILE_PAGE, RouterType.FORWARD);
        return router;
    }
}

