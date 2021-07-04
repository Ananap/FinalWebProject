package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateBasketCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(UpdateBasketCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;
        final String basketFlowerId = req.getParameter(RequestParameter.BASKET_ID);
        final String count = req.getParameter(RequestParameter.COUNT);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            basketFlowerService.updateBasketFlower(basketFlowerId, count);
            router = new Router(PagePath.GO_TO_BASKET_PAGE, RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Error at UpdateBasketCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
