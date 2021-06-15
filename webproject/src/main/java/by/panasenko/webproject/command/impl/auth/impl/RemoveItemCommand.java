package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveItemCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(RemoveItemCommand.class);

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final String basketFlowerId = req.getParameter(RequestParameter.BASKET_FLOWER_ID);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            basketFlowerService.deleteBasketFlower(basketFlowerId);
            router = new Router(PagePath.GO_TO_BASKET_PAGE, Router.RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Error at UpdateBasketCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
