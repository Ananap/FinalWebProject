package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession(false).invalidate();
        Router router = new Router(PagePath.ABOUT_PAGE, RouterType.REDIRECT);
        return router;
    }
}
