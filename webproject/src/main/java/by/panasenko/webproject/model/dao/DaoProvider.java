package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.model.dao.impl.*;

/**
 * Factory class that provides implementations of DAO interfaces.
 */
public class DaoProvider {

    /**
     * Instance of the class
     */
    private static final DaoProvider instance = new DaoProvider();
    /**
     * An object of {@link UserDaoImpl}
     */
    private static final UserDao userDao = UserDaoImpl.getInstance();
    /**
     * An object of {@link FlowerDaoImpl}
     */
    private static final FlowerDao flowerDao = FlowerDaoImpl.getInstance();
    /**
     * An object of {@link FlowerTypeDaoImpl}
     */
    private static final FlowerTypeDao flowerTypeDao = FlowerTypeDaoImpl.getInstance();
    /**
     * An object of {@link StorageDaoImpl}
     */
    private static final StorageDao storageDao = StorageDaoImpl.getInstance();
    /**
     * An object of {@link BasketDaoImpl}
     */
    private static final BasketDao basketDao = BasketDaoImpl.getInstance();
    /**
     * An object of {@link OrderDaoImpl}
     */
    private static final OrderDao orderDao = OrderDaoImpl.getInstance();
    /**
     * An object of {@link BasketFlowerDaoImpl}
     */
    private static final BasketFlowerDao basketFlowerDao = BasketFlowerDaoImpl.getInstance();
    /**
     * An object of {@link OrderFlowerDaoImpl}
     */
    private static final OrderFlowerDao orderFlowerDao = OrderFlowerDaoImpl.getInstance();

    /**
     * Private constructor without parameters
     */
    private DaoProvider() {
    }

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link DaoProvider}
     */
    public static DaoProvider getInstance() {
        return instance;
    }

    /**
     * Method returns field of {@link UserDao} object
     *
     * @return {@link UserDao} object
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Method returns field of {@link FlowerDao} object
     *
     * @return {@link FlowerDao} object
     */
    public static FlowerDao getFlowerDao() {
        return flowerDao;
    }

    /**
     * Method returns field of {@link FlowerTypeDao} object
     *
     * @return {@link FlowerTypeDao} object
     */
    public static FlowerTypeDao getFlowerTypeDao() {
        return flowerTypeDao;
    }

    /**
     * Method returns field of {@link StorageDao} object
     *
     * @return {@link StorageDao} object
     */
    public static StorageDao getStorageDao() {
        return storageDao;
    }

    /**
     * Method returns field of {@link BasketDao} object
     *
     * @return {@link BasketDao} object
     */
    public static BasketDao getBasketDao() {
        return basketDao;
    }

    /**
     * Method returns field of {@link BasketFlowerDao} object
     *
     * @return {@link BasketFlowerDao} object
     */
    public static BasketFlowerDao getBasketFlowerDao() {
        return basketFlowerDao;
    }

    /**
     * Method returns field of {@link OrderDao} object
     *
     * @return {@link OrderDao} object
     */
    public static OrderDao getOrderDao() {
        return orderDao;
    }

    /**
     * Method returns field of {@link OrderFlowerDao} object
     *
     * @return {@link OrderFlowerDao} object
     */
    public static OrderFlowerDao getOrderFlowerDao() {
        return orderFlowerDao;
    }
}
