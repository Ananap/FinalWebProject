package by.panasenko.webproject.command.impl.admin.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteItemCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(DeleteItemCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final String flowerId = req.getParameter(RequestParameter.FLOWER_ID);
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();

        try {
            flowerService.deleteFlowerById(flowerId);
            router = new Router(PagePath.GO_TO_FLOWER_LIST, RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Error at DeleteItemCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
