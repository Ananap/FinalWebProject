package by.panasenko.webproject.command.impl.auth.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToProfilePageCommand extends AuthCommand {
    private static final String ATTRIBUTE_ACTIVE_EDIT = "activeEdit";

    @Override
    public Router process(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(ATTRIBUTE_ACTIVE_EDIT, true);
        Router router = new Router(PagePath.PROFILE_PAGE, RouterType.FORWARD);
        return router;
    }
}

