package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddItemToBasketCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(AddItemToBasketCommand.class);

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final User user = (User) req.getSession().getAttribute(RequestAttribute.USER);

        final String flowerId = req.getParameter(RequestParameter.FLOWER_ID);
        final String count = req.getParameter(RequestParameter.FLOWER_COUNT);
        final String price = req.getParameter(RequestParameter.FLOWER_PRICE);
        final String storageAmount = req.getParameter(RequestParameter.STORAGE_AMOUNT);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            Basket basket = user.getBasket();
            basketFlowerService.addToBasket(basket.getId(), flowerId, count, price);
            if (Integer.parseInt(count) > Integer.parseInt(storageAmount)) {
                req.setAttribute(RequestAttribute.NOT_ENOUGH, true);
            } else {
                req.setAttribute(RequestAttribute.ADD_SUCCESS, true);
            }
            req.setAttribute(RequestAttribute.FLOWER_ID, flowerId);
            router = new Router(PagePath.GO_TO_ITEM_DETAIL_COMMAND, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at AddItemToBasketCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
