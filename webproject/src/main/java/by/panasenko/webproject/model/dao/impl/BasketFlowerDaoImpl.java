package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.BasketFlowerDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.panasenko.webproject.model.dao.ColumnName.*;

/**
 * Implementation of {@link BasketFlowerDao}. Provides methods to interact with BasketFlower data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class BasketFlowerDaoImpl implements BasketFlowerDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final BasketFlowerDaoImpl instance = new BasketFlowerDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to select basketflower by id */
    private static final String SELECT_BY_ID_SQL = "SELECT basket_flower_id, count, sub_total FROM basket_flower WHERE (basket_flower_id = ?)";

    /** Query for database to select items by basket id */
    private static final String SELECT_ITEMS_BY_BASKET_ID_SQL = "SELECT basket_flower_id, basket_id, flower_id, count, sub_total, flower_image, name, price, storage_count FROM basket_flower " +
            "INNER JOIN flower f ON basket_flower.flower_id = f.id " +
            "INNER JOIN storage s on f.id = s.flowers_id " +
            "WHERE (basket_id = ?)";

    /** Query for database to add item to basket */
    private static final String ADD_ITEM_SQL = "INSERT INTO basket_flower (basket_id, flower_id, count, sub_total) VALUES (?,?,?,?)";

    /** Query for database to set sub total cost */
    private static final String SET_SUB_TOTAL_SQL = "UPDATE basket_flower SET sub_total = ? WHERE basket_flower_id = ?";

    /** Query for database to set count */
    private static final String SET_COUNT_SQL = "UPDATE basket_flower SET count = ? WHERE basket_flower_id = ?";

    /** Query for database to delete basketFlower by id */
    private static final String DELETE_ITEM_SQL = "DELETE FROM basket_flower WHERE basket_flower_id = ?";

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link BasketFlowerDaoImpl}
     */
    public static BasketFlowerDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private BasketFlowerDaoImpl() {
    }

    /**
     * Connects to database and returns all info about basket flower by ID.
     *
     * @param id is basket flower ID value.
     * @return {@link BasketFlower} if basket flower data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public BasketFlower findById(Integer id) throws DaoException {
        BasketFlower basketFlower = new BasketFlower();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setInt(ItemIndex.BASKET_ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                basketFlower.setId(resultSet.getInt(BASKET_FLOWER_ID));
                basketFlower.setCount(resultSet.getInt(BASKET_FLOWER_COUNT));
                basketFlower.setSubTotal(resultSet.getBigDecimal(BASKET_FLOWER_SUB_TOTAL));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketFlowerDao.findById request", e);
        }
        return basketFlower;
    }

    /**
     * Connects to database and delete basket flower by ID.
     *
     * @param id is basket flower ID value
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void deleteBasketFlower(Integer id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_SQL)) {
            statement.setInt(ItemIndex.BASKET_ID, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketFlowerDao.deleteBasketFlower request", e);
        }
    }

    /**
     * Connects to database and add new flower to basket.
     *
     * @param id       is basket ID value.
     * @param flowerId is flower ID value
     * @param count    is amount of the flower in basket
     * @param subTotal is sub total of user's basket, calculated as count of the flower multiplied by its price
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void addItemToBasket(Integer id, Integer flowerId, Integer count, BigDecimal subTotal) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ITEM_SQL)) {
            statement.setInt(AddItemIndex.BASKET_ID, id);
            statement.setInt(AddItemIndex.FLOWER_ID, flowerId);
            statement.setInt(AddItemIndex.COUNT, count);
            statement.setBigDecimal(AddItemIndex.SUB_TOTAL, subTotal);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketFlowerDao.addItemToBasket request", e);
        }
    }

    /**
     * Connects to database and returns all info about basket by its ID.
     *
     * @param id is basket ID value.
     * @return List of {@link BasketFlower} with all matching data.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public List<BasketFlower> findByBasketId(Integer id) throws DaoException {
        List<BasketFlower> basketFlowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ITEMS_BY_BASKET_ID_SQL)) {
            statement.setInt(ItemIndex.BASKET_ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BasketFlower basketFlower = new BasketFlower();
                Flower flower = new Flower();
                Basket basket = new Basket();
                Storage storage = new Storage();
                basketFlower.setId(resultSet.getInt(BASKET_FLOWER_ID));
                basketFlower.setBasket(basket);
                basketFlower.setFlower(flower);
                basketFlower.setSubTotal(resultSet.getBigDecimal(BASKET_FLOWER_SUB_TOTAL));
                basketFlower.setCount(resultSet.getInt(BASKET_FLOWER_COUNT));
                flower.setId(resultSet.getInt(BASKET_FLOWER_FLOWER_ID));
                flower.setFlowerImage(resultSet.getString(FLOWER_IMAGE));
                flower.setName(resultSet.getString(FLOWER_NAME));
                flower.setStorage(storage);
                flower.setPrice(resultSet.getDouble(FLOWER_PRICE));
                basket.setId(resultSet.getInt(BASKET_FLOWER_BASKET_ID));
                storage.setCount(resultSet.getInt(STORAGE_COUNT));
                basketFlowerList.add(basketFlower);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketFlowerDao.findByBasketId request", e);
        }
        return basketFlowerList;
    }

    /**
     * Connects to database and updates sub total of basket.
     *
     * @param basketFlower is {@link BasketFlower} object that contains all info about basket flower for update.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void updateSubTotal(BasketFlower basketFlower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_SUB_TOTAL_SQL)) {
            statement.setBigDecimal(SetSubTotalIndex.SUB_TOTAL, basketFlower.getSubTotal());
            statement.setInt(SetSubTotalIndex.ID, basketFlower.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketFlowerDao.updateBasketFlower request", e);
        }
    }

    /**
     * Connects to database and updates count of flower in basket.
     *
     * @param basketFlower is {@link BasketFlower} object that contains all info about basket flower for update.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void updateCount(BasketFlower basketFlower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_COUNT_SQL)) {
            statement.setInt(SetSubTotalIndex.SUB_TOTAL, basketFlower.getCount());
            statement.setInt(SetSubTotalIndex.ID, basketFlower.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketFlowerDao.setCountBasketFlower request", e);
        }
    }

    /**
     * Static class that contains parameter indexes for adding item to basket
     */
    private static class AddItemIndex {
        private static final int BASKET_ID = 1;
        private static final int FLOWER_ID = 2;
        private static final int COUNT = 3;
        private static final int SUB_TOTAL = 4;
    }


    /**
     * Static class that contains parameter indexes for finding items from basket
     */
    private static class ItemIndex {
        private static final int BASKET_ID = 1;
    }

    /**
     * Static class that contains parameter indexes for setting sub_total
     */
    private static class SetSubTotalIndex {
        private static final int SUB_TOTAL = 1;
        private static final int ID = 2;
    }
}
