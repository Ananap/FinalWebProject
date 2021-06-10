package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.BasketFlowerDao;
import by.panasenko.webproject.model.dao.ColumnName;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketFlowerDaoImpl implements BasketFlowerDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final BasketFlowerDaoImpl instance = new BasketFlowerDaoImpl();

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Query for database to add item to basket
     */
    private static final String ADD_ITEM_SQL = "INSERT INTO basket_flower (basket_id, flower_id, count, sub_total) VALUES (?,?,?,?)";

    /**
     * Query for database to select items by basket id
     */
    private static final String SELECT_ITEMS_BY_BASKET_ID_SQL = "SELECT basket_flower_id, basket_id, flower_id, count, flower_image, name, price, storage_count FROM basket_flower " +
            "JOIN flower f ON basket_flower.flower_id = f.id " +
            "JOIN storage s on f.id = s.flowers_id " +
            "WHERE (basket_id = ?)";

    /**
     * Query for database to set sub total cost
     */
    private static final String SET_SUB_TOTAL = "UPDATE basket_flower SET sub_total = ? WHERE basket_flower_id = ?";

    /**
     * Message, that is putted in Exception if there are add item to basket problem
     */
    private static final String MESSAGE_ADD_ITEM_PROBLEM = "Can't handle BasketFlowerDao.addItemToBasket request";

    /**
     * Message, that is putted in Exception if there find by basket problem problem
     */
    private static final String MESSAGE_FIND_BY_BASKET_PROBLEM = "Can't handle BasketFlowerDao.findByBasketId request";

    /**
     * Message, that is putted in Exception if there find by basket problem problem
     */
    private static final String MESSAGE_SET_SUB_TOTAL_PROBLEM = "Can't handle BasketFlowerDao.updateBasketFlower request";

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

    @Override
    public void addItemToBasket(int id, String flowerId, String count, BigDecimal subTotal) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ITEM_SQL)) {
            statement.setInt(AddItemIndex.BASKET_ID, id);
            statement.setInt(AddItemIndex.FLOWER_ID, Integer.parseInt(flowerId));
            statement.setInt(AddItemIndex.COUNT, Integer.parseInt(count));
            statement.setBigDecimal(AddItemIndex.SUB_TOTAL, subTotal);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_ADD_ITEM_PROBLEM, e);
        }
    }

    @Override
    public List<BasketFlower> findByBasketId(int id) throws DaoException {
        List<BasketFlower> basketFlowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ITEMS_BY_BASKET_ID_SQL)) {
            statement.setInt(FindItemIndex.BASKET_ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Storage storage = new Storage();
                storage.setCount(resultSet.getInt(ColumnName.STORAGE_COUNT));
                Flower flower = new Flower();
                flower.setId(resultSet.getInt(ColumnName.BASKET_FLOWER_FLOWER_ID));
                flower.setFlowerImage(resultSet.getString(ColumnName.FLOWER_IMAGE));
                flower.setName(resultSet.getString(ColumnName.FLOWER_NAME));
                flower.setStorage(storage);
                flower.setPrice(resultSet.getDouble(ColumnName.FLOWER_PRICE));
                Basket basket = new Basket();
                basket.setId(resultSet.getInt(ColumnName.BASKET_FLOWER_BASKET_ID));
                BasketFlower basketFlower = new BasketFlower();
                basketFlower.setId(resultSet.getInt(ColumnName.BASKET_FLOWER_ID));
                basketFlower.setBasket(basket);
                basketFlower.setFlower(flower);
                basketFlower.setCount(resultSet.getInt(ColumnName.BASKET_FLOWER_COUNT));
                basketFlowerList.add(basketFlower);
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_FIND_BY_BASKET_PROBLEM, e);
        }
        return basketFlowerList;
    }

    @Override
    public void updateBasketFlower(BasketFlower basketFlower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_SUB_TOTAL)) {
            statement.setBigDecimal(SetSubTotalIndex.SUB_TOTAL, basketFlower.getSubTotal());
            statement.setInt(SetSubTotalIndex.ID, basketFlower.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SET_SUB_TOTAL_PROBLEM, e);
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
    private static class FindItemIndex {
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
