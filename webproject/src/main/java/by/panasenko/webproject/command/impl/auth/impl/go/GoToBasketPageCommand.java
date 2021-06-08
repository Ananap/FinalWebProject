package by.panasenko.webproject.command.impl.auth.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.model.service.BasketService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GoToBasketPageCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(GoToBasketPageCommand.class);
    private static final String ERROR_MESSAGE = "Error at GoToBasketPageCommand";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_BASKET_FLOWER_LIST = "basketFlowerList";
    private static final String ATTRIBUTE_BASKET = "basket";
    private static final String ATTRIBUTE_EMPTY_BASKET = "emptyBasket";

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketService basketService = serviceProvider.getBasketService();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            Basket basket = user.getBasket();
            List<BasketFlower> basketFlowerList = basketFlowerService.findByBasketId(basket.getId());
            req.setAttribute(ATTRIBUTE_BASKET_FLOWER_LIST, basketFlowerList);
            // TODO update basket (count and set sub total)
            basketService.updateBasket(basket, basketFlowerList);
            if (basketFlowerList.isEmpty()) {
                req.setAttribute(ATTRIBUTE_EMPTY_BASKET, true);
            }
            req.setAttribute(ATTRIBUTE_BASKET, basket);
            router = new Router(PagePath.BASKET_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.FORWARD);
        }
        return router;
    }
}
