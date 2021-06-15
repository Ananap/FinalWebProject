package by.panasenko.webproject.model.service;

import by.panasenko.webproject.model.service.impl.*;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final FlowerService flowerService = new FlowerServiceImpl();
    private final FlowerTypeService flowerTypeService = new FlowerTypeServiceImpl();
    private final StorageService storageService = new StorageServiceImpl();
    private final BasketService basketService = new BasketServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final BasketFlowerService basketFlowerService = new BasketFlowerServiceImpl();

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

    public FlowerTypeService getFlowerTypeService() {
        return flowerTypeService;
    }

    public StorageService getStorageService() {
        return storageService;
    }

    public BasketService getBasketService() {
        return basketService;
    }

    public BasketFlowerService getBasketFlowerService() {
        return basketFlowerService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
