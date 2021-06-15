package by.panasenko.webproject.command.impl.admin.impl.go;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.FlowerTypeService;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.util.RegexpPropertyUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GoToUpdateItemPageCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToUpdateItemPageCommand.class);
    private static final String REGEXP_PROP_NAME = "regexp.flower.name";
    private static final String REGEXP_PROP_PRICE = "regexp.flower.price";
    private static final String REGEXP_PROP_WATERING = "regexp.flower.watering";
    private static final String REGEXP_PROP_QUANTITY = "regexp.flower.quantity";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final String flowerId = req.getParameter(RequestParameter.FLOWER_ID);
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final FlowerTypeService flowerTypeService = serviceProvider.getFlowerTypeService();

        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

        final String regexpName = regexpPropertyUtil.getProperty(REGEXP_PROP_NAME);
        final String regexpPrice = regexpPropertyUtil.getProperty(REGEXP_PROP_PRICE);
        final String regexpWatering = regexpPropertyUtil.getProperty(REGEXP_PROP_WATERING);
        final String regexpQuantity = regexpPropertyUtil.getProperty(REGEXP_PROP_QUANTITY);

        try {
            Flower flower = flowerService.findById(flowerId);
            List<FlowerType> flowerTypeList = flowerTypeService.findAll();
            req.setAttribute(RequestAttribute.FLOWER_TYPE_LIST, flowerTypeList);
            req.setAttribute(RequestAttribute.FLOWER, flower);
            req.setAttribute(RequestAttribute.REGEXP_NAME, regexpName);
            req.setAttribute(RequestAttribute.REGEXP_PRICE, regexpPrice);
            req.setAttribute(RequestAttribute.REGEXP_WATER, regexpWatering);
            req.setAttribute(RequestAttribute.REGEXP_COUNT, regexpQuantity);
            router = new Router(PagePath.UPDATE_ITEM_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToUpdateItemPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
