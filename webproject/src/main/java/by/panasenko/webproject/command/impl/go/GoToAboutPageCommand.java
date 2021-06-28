package by.panasenko.webproject.command.impl.go;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class GoToAboutPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.ABOUT_PAGE, RouterType.REDIRECT);
        return router;
    }
}
