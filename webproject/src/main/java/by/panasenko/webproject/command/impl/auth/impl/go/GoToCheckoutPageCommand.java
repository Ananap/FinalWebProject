package by.panasenko.webproject.command.impl.auth.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.model.service.BasketService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GoToCheckoutPageCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(GoToCheckoutPageCommand.class);

    @Override
    protected Router process(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        final String basketId = req.getParameter(RequestParameter.BASKET_ID);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final BasketService basketService = serviceProvider.getBasketService();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();

        try {
            Basket basket = basketService.findById(basketId);
            List<BasketFlower> basketFlowerList = basketFlowerService.findByBasketId(Integer.parseInt(basketId));
            if (basketFlowerList.size() == 0) {
                req.setAttribute(RequestAttribute.EMPTY_BASKET, true);
                router = new Router(PagePath.BASKET_PAGE, Router.RouterType.FORWARD);
            } else {
                req.setAttribute(RequestAttribute.BASKET, basket);
                req.setAttribute(RequestAttribute.BASKET_FLOWER_LIST, basketFlowerList);
                router = new Router(PagePath.CHECKOUT_PAGE, Router.RouterType.FORWARD);
            }
            for (BasketFlower basketFlower : basketFlowerList) {
                if (basketFlower.getFlower().getStorage().getCount() < basketFlower.getCount()) {
                    req.setAttribute(RequestAttribute.NOT_ENOUGH, true);
                    router = new Router(PagePath.BASKET_PAGE, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException e) {
            logger.error("Error at GoToCheckoutPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
