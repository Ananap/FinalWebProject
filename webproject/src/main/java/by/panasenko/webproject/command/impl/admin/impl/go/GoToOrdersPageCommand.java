package by.panasenko.webproject.command.impl.admin.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.OrderService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToOrdersPageCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToOrdersPageCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrderService orderService = serviceProvider.getOrderService();

        try {
            List<Order> orderList = orderService.findAll();
            req.setAttribute(RequestAttribute.ORDER_LIST, orderList);
            router = new Router(PagePath.ORDER_ALL_INFO_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToOrdersPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
