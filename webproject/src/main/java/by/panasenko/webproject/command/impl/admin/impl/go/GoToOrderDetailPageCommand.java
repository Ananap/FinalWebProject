package by.panasenko.webproject.command.impl.admin.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.entity.OrderFlower;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.OrderService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToOrderDetailPageCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToOrderDetailPageCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrderService orderService = serviceProvider.getOrderService();
        final String orderId = req.getParameter(RequestParameter.ORDER_ID);

        try {
            Order order = orderService.findById(orderId);
            List<OrderFlower> orderFlowerList = orderService.findByOrder(order.getId());
            req.setAttribute(RequestAttribute.ORDER, order);
            req.setAttribute(RequestAttribute.ORDER_FLOWER, orderFlowerList);
            router = new Router(PagePath.ORDER_DETAIL_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToOrderDetailPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
