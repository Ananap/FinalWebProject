package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
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
    private static final String ERROR_MESSAGE = "Error at AddItemToBasketCommand";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_FLOWER_ID = "flowerId";
    private static final String ATTRIBUTE_FLOWER_PRICE = "flowerPrice";
    private static final String ATTRIBUTE_FLOWER_COUNT = "amount";
    private static final String ATTRIBUTE_STORAGE_AMOUNT = "storageAmount";
    private static final String ATTRIBUTE_NOT_ENOUGH = "notEnoughStorage";
    private static final String ATTRIBUTE_ADD_SUCCESS = "addFlowerSuccess";

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        final String flowerId = req.getParameter(ATTRIBUTE_FLOWER_ID);
        final String count = req.getParameter(ATTRIBUTE_FLOWER_COUNT);
        final String price = req.getParameter(ATTRIBUTE_FLOWER_PRICE);
        final String storageAmount = req.getParameter(ATTRIBUTE_STORAGE_AMOUNT);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            Basket basket = user.getBasket();
            basketFlowerService.addToBasket(basket.getId(), flowerId, count, price);
            if (Integer.parseInt(count) > Integer.parseInt(storageAmount)) {
                req.setAttribute(ATTRIBUTE_NOT_ENOUGH, true);
            } else {
                req.setAttribute(ATTRIBUTE_ADD_SUCCESS, true);
            }
            req.setAttribute(ATTRIBUTE_FLOWER_ID, flowerId);
            router = new Router(PagePath.GO_TO_ITEM_DETAIL_COMMAND, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
