package by.panasenko.webproject.command.impl.admin.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToFlowerListPageCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToFlowerListPageCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();

        try {
            List<Flower> flowerList = flowerService.findAllFlowerList();
            req.setAttribute(RequestAttribute.FLOWER_LIST, flowerList);
            router = new Router(PagePath.FLOWER_LIST_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToFlowerListPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
