package by.panasenko.webproject.command.impl.admin.impl;

import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
import by.panasenko.webproject.command.RequestParameter;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.command.impl.admin.AdminCommand;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.FlowerTypeService;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.StorageService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateItemCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(UpdateItemCommand.class);

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final String flowerId = req.getParameter(RequestParameter.FLOWER_ID);
        final String nameFlower = req.getParameter(RequestParameter.NAME);
        final String description = req.getParameter(RequestParameter.DESCRIPTION);
        final String category = req.getParameter(RequestParameter.CATEGORY);
        final String soil = req.getParameter(RequestParameter.SOIL);
        final String watering = req.getParameter(RequestParameter.WATERING);
        final String country = req.getParameter(RequestParameter.COUNTRY);
        final String light = req.getParameter(RequestParameter.LIGHT);
        final String price = req.getParameter(RequestParameter.PRICE);
        final String count = req.getParameter(RequestParameter.COUNT);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final FlowerTypeService flowerTypeService = serviceProvider.getFlowerTypeService();
        final StorageService storageService = serviceProvider.getStorageService();

        try {
            FlowerType flowerType = flowerTypeService.findById(category);
            flowerService.updateFlower(flowerId, nameFlower, description, price, soil, watering, light, country, flowerType);
            storageService.updateStorage(flowerId, count);
            router = new Router(PagePath.GO_TO_FLOWER_LIST, RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Error at UpdateItemCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
