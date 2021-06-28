package by.panasenko.webproject.command.impl.go;

import by.panasenko.webproject.command.*;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.StorageService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class GoToFlowerDetailPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(GoToFlowerDetailPageCommand.class);

    @Override
    public Router execute(HttpServletRequest req) {
        Router router;
        String flowerId = req.getParameter(RequestParameter.FLOWER_ID);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final StorageService storageService = serviceProvider.getStorageService();

        try {
            Flower flower = flowerService.findById(flowerId);
            Storage storage = storageService.findByFlowerId(flowerId);
            req.setAttribute(RequestAttribute.FLOWER, flower);
            req.setAttribute(RequestAttribute.STORAGE, storage);
            router = new Router(PagePath.ITEM_DETAIL_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at FlowerDetailCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
        }
        return router;
    }
}
