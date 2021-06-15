package by.panasenko.webproject.command.impl.admin.impl.go;

import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//todo
public class GoToOrdersPageCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToFlowerListPageCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        return null;
    }
}
