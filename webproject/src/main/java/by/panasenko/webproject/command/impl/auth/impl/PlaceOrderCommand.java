package by.panasenko.webproject.command.impl.auth.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.model.service.BasketService;
import by.panasenko.webproject.model.service.OrderService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class PlaceOrderCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(PlaceOrderCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;
        final User user = (User) req.getSession().getAttribute(RequestAttribute.USER);
        final String address = req.getParameter(RequestParameter.ADDRESS);
        final String cash = req.getParameter(RequestParameter.CASH);
        final String date = req.getParameter(RequestParameter.DATE);
        final String time = req.getParameter(RequestParameter.TIME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrderService orderService = serviceProvider.getOrderService();
        final BasketFlowerService basketFlowerService = serviceProvider.getBasketFlowerService();
        final BasketService basketService = serviceProvider.getBasketService();

        try {
            Basket basket = user.getBasket();
            List<BasketFlower> basketFlowerList = basketFlowerService.findByBasketId(basket.getId());
            Optional<Order> order = orderService.createOrder(address, cash, date, time, user, basket);
            if (order.isPresent()) {
                Order savedOrder = orderService.saveOrder(order.get());
                orderService.createOrderFlowerByOrder(savedOrder, basketFlowerList);
                Basket clearedBasket = basketFlowerService.clearBasket(basket);
                basketService.updateTotalCost(clearedBasket);
                router = new Router(PagePath.ORDER_SUBMIT, RouterType.REDIRECT);
            } else {
                req.setAttribute(RequestAttribute.MISS_REQUIRED_FIELDS, true);
                router = new Router(PagePath.GO_TO_CHECKOUT_PAGE, RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Error at PlaceOrderCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.FORWARD);
        }
        return router;
    }
}
