package by.panasenko.webproject.command.impl.admin.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.OrderService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeOrderStatusCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(ChangeOrderStatusCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final String orderStatus = req.getParameter(RequestParameter.ORDER_STATUS);
        final String orderId = req.getParameter(RequestParameter.ORDER_ID);
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrderService orderService = serviceProvider.getOrderService();

        try {
            orderService.changeStatus(orderStatus, orderId);
            router = new Router(PagePath.GO_TO_ORDER_LIST, RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Error at ChangeOrderStatusCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
