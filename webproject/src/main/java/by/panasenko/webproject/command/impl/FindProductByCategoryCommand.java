package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.FlowerTypeService;
import by.panasenko.webproject.model.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindProductByCategoryCommand implements Command {
    private static final Logger logger = Logger.getLogger(FindProductByCategoryCommand.class);
    private static final String ERROR_MESSAGE = "Error at FindProductByCategoryCommand";
    private static final String ATTRIBUTE_CATEGORY = "category";
    private static final String ATTRIBUTE_FLOWER_TYPE = "flowerTypeSelected";
    private static final String ATTRIBUTE_FLOWER_LIST = "flowerList";
    private static final String ATTRIBUTE_EMPTY_LIST = "emptyList";
    private static final String ATTRIBUTE_EXCEPTION = "exception";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse res) {
        Router router;
        String category = req.getParameter(ATTRIBUTE_CATEGORY);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final FlowerTypeService flowerTypeService = serviceProvider.getFlowerTypeService();

        try {
            List<Flower> flowerList = flowerService.findByCategory(category);
            FlowerType flowerType = flowerTypeService.findById(category);
            req.setAttribute(ATTRIBUTE_FLOWER_TYPE, flowerType);
            if (flowerList.isEmpty()) {
                req.setAttribute(ATTRIBUTE_EMPTY_LIST, true);
            } else {
                req.setAttribute(ATTRIBUTE_FLOWER_LIST, flowerList);
            }
            router = new Router(PagePath.ITEM_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
