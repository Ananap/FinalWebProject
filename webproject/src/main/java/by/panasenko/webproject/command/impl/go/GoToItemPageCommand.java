package by.panasenko.webproject.command.impl.go;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.RequestAttribute;
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

public class GoToItemPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(GoToItemPageCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse res) {
        Router router;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final FlowerService flowerService = serviceProvider.getFlowerService();
        final FlowerTypeService flowerTypeService = serviceProvider.getFlowerTypeService();

        try {
            List<FlowerType> flowerTypeList = flowerTypeService.findAll();
            List<Flower> flowerList = flowerService.findAll();
            req.setAttribute(RequestAttribute.FLOWER_LIST, flowerList);
            req.setAttribute(RequestAttribute.FLOWER_TYPE_LIST, flowerTypeList);
            if (flowerList.isEmpty()) {
                req.setAttribute(RequestAttribute.EMPTY_LIST, true);
            }
            router = new Router(PagePath.ITEM_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error at GoToItemPageCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.FORWARD);
        }
        return router;
    }
}
