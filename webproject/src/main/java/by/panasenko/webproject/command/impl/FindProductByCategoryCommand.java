package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.*;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.FlowerTypeService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindProductByCategoryCommand implements Command {
    private static final Logger logger = Logger.getLogger(FindProductByCategoryCommand.class);

    @Override
    public Router execute(HttpServletRequest req) {
        Router router;
        final String category = req.getParameter(RequestParameter.CATEGORY);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final FlowerTypeService flowerTypeService = serviceProvider.getFlowerTypeService();

        try {
            List<Flower> flowerList = flowerService.findByCategory(category);
            FlowerType flowerType = flowerTypeService.findById(category);
            req.setAttribute(RequestAttribute.FLOWER_TYPE, flowerType);
            if (flowerList.isEmpty()) {
                req.setAttribute(RequestAttribute.EMPTY_LIST, true);
            } else {
                req.setAttribute(RequestAttribute.FLOWER_LIST, flowerList);
            }
            router = new Router(PagePath.ITEM_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at FindProductByCategoryCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
