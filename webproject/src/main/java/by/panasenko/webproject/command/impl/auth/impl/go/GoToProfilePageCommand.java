package by.panasenko.webproject.command.impl.auth.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.auth.AuthCommand;
import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.OrderService;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.util.RegexpPropertyUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToProfilePageCommand extends AuthCommand {
    private static final Logger logger = Logger.getLogger(GoToProfilePageCommand.class);
    private static final String REGEXP_PROP_USERNAME = "regexp.username";
    private static final String REGEXP_PROP_FIO = "regexp.user_fio";
    private static final String REGEXP_PROP_PHONE_NUMBER = "regexp.phone_number";

    @Override
    public Router process(HttpServletRequest req) {
        Router router;
        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

        final User user = (User) req.getSession().getAttribute(RequestAttribute.USER);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrderService orderService = serviceProvider.getOrderService();

        final String REGEXP_USERNAME = regexpPropertyUtil.getProperty(REGEXP_PROP_USERNAME);
        final String REGEXP_FIO = regexpPropertyUtil.getProperty(REGEXP_PROP_FIO);
        final String REGEXP_PHONE_NUMBER = regexpPropertyUtil.getProperty(REGEXP_PROP_PHONE_NUMBER);

        try {
            List<Order> orderList = orderService.findByUser(user.getId());
            req.setAttribute(RequestAttribute.REGEXP_USERNAME, REGEXP_USERNAME);
            req.setAttribute(RequestAttribute.REGEXP_FIO, REGEXP_FIO);
            req.setAttribute(RequestAttribute.REGEXP_PHONE, REGEXP_PHONE_NUMBER);
            req.setAttribute(RequestAttribute.ORDER_LIST, orderList);
            req.setAttribute(RequestAttribute.ACTIVE_EDIT, true);
            router = new Router(PagePath.PROFILE_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToProfilePageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}

