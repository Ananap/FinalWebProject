package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.StorageService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlowerDetailCommand implements Command {
    private static final Logger logger = Logger.getLogger(FlowerDetailCommand.class);
    private static final String ERROR_MESSAGE = "Error at FlowerDetailCommand";
    private static final String ATTRIBUTE_FLOWER_ID = "flowerId";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_FLOWER = "flower";
    private static final String ATTRIBUTE_STORAGE = "storage";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse res) {
        Router router;
        String flowerId = req.getParameter(ATTRIBUTE_FLOWER_ID);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final StorageService storageService = serviceProvider.getStorageService();

        try {
            Flower flower = flowerService.findById(flowerId);
            Storage storage = storageService.findByFlowerId(flowerId);
            req.setAttribute(ATTRIBUTE_FLOWER, flower);
            req.setAttribute(ATTRIBUTE_STORAGE, storage);
            router = new Router(PagePath.ITEM_DETAIL_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
