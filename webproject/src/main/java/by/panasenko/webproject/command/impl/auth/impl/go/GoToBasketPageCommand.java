package by.panasenko.webproject.command.impl.auth.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
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

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final User user = (User) req.getSession().getAttribute(RequestAttribute.USER);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketService basketService = serviceProvider.getBasketService();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            Basket basket = user.getBasket();
            List<BasketFlower> basketFlowerList = basketFlowerService.findByBasketId(basket.getId());
            req.setAttribute(RequestAttribute.BASKET_FLOWER_LIST, basketFlowerList);
            basketService.updateBasket(basket, basketFlowerList);
            if (basketFlowerList.isEmpty()) {
                req.setAttribute(RequestAttribute.EMPTY_BASKET, true);
            }
            req.setAttribute(RequestAttribute.BASKET, basket);
            router = new Router(PagePath.BASKET_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToBasketPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
