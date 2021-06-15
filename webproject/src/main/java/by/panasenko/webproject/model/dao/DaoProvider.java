package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.model.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final FlowerDao flowerDao = FlowerDaoImpl.getInstance();
    private static final FlowerTypeDao flowerTypeDao = FlowerTypeDaoImpl.getInstance();
    private static final StorageDao storageDao = StorageDaoImpl.getInstance();
    private static final BasketDao basketDao = BasketDaoImpl.getInstance();
    private static final OrderDao orderDao = OrderDaoImpl.getInstance();
    private static final BasketFlowerDao basketFlowerDao = BasketFlowerDaoImpl.getInstance();
    private static final OrderFlowerDao orderFlowerDao = OrderFlowerDaoImpl.getInstance();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public static FlowerDao getFlowerDao() {
        return flowerDao;
    }

    public static FlowerTypeDao getFlowerTypeDao() {
        return flowerTypeDao;
    }

    public static StorageDao getStorageDao() {
        return storageDao;
    }

    public static BasketDao getBasketDao() {
        return basketDao;
    }

    public static BasketFlowerDao getBasketFlowerDao() {
        return basketFlowerDao;
    }

    public static OrderDao getOrderDao() {
        return orderDao;
    }

    public static OrderFlowerDao getOrderFlowerDao() {
        return orderFlowerDao;
    }
}
