package by.panasenko.webproject.command.impl.admin.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToItemInfoPageCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToItemInfoPageCommand.class);

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final String flowerId = req.getParameter(RequestParameter.FLOWER_ID);

        try {
            Flower flower = flowerService.findById(flowerId);
            req.setAttribute(RequestAttribute.FLOWER, flower);
            router = new Router(PagePath.ITEM_INFO_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToItemInfoPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
