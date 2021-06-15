package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.*;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.service.BasketService;
import by.panasenko.webproject.model.service.ServiceProvider;
import by.panasenko.webproject.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignInCommand implements Command {
    private static final Logger logger = Logger.getLogger(SignInCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        String email = req.getParameter(RequestParameter.EMAIL);
        String password = req.getParameter(RequestParameter.PASSWORD);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        final BasketService basketService = serviceProvider.getBasketService();

        SignInData signInData = new SignInData();
        signInData.setEmail(email);
        signInData.setPassword(password);

        try {
            Optional<User> optionalUser = userService.signIn(signInData);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Basket basket = basketService.findUserBasket(user.getId());
                user.setBasket(basket);
                req.getSession().setAttribute(RequestAttribute.USER, user);
                req.getSession().setAttribute(RequestAttribute.ROLE, user.getRole());
                router = new Router(PagePath.GO_TO_PROFILE_PAGE, RouterType.REDIRECT);
            } else {
                req.setAttribute(RequestAttribute.WRONG_DATA, true);
                router = new Router(PagePath.GO_TO_LOGIN_PAGE, RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Error at SignInCommand", e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, RouterType.REDIRECT);
        }
        return router;
    }
}
