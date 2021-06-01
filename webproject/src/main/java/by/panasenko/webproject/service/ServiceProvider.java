package by.panasenko.webproject.service;

import by.panasenko.webproject.service.impl.FlowerServiceImpl;
import by.panasenko.webproject.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final FlowerService flowerService = new FlowerServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public FlowerService getFlowerService() {
        return flowerService;
    }
}
