package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.BasketDao;
import by.panasenko.webproject.model.dao.ColumnName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasketDaoImpl implements BasketDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final BasketDaoImpl instance = new BasketDaoImpl();

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Query for database to get basket by user id
     */
    private static final String FIND_BASKET_BY_USER = "SELECT id, user_id_foreign, total_cost FROM basket b " +
            "JOIN users u ON b.user_id_foreign = u.user_id " +
            "WHERE (user_id_foreign = ?)";

    /**
     * Query for database to get basket by id
     */
    private static final String FIND_BASKET_BY_ID = "SELECT id, total_cost FROM basket b " +
            "WHERE (id = ?)";

    /**
     * Query for database to create basket
     */
    private static final String INSERT_BASKET_SQL = "INSERT INTO basket (user_id_foreign) VALUE (?)";

    /**
     * Query for database to set total cost to basket
     */
    private static final String SET_TOTAL_COST = "UPDATE basket SET total_cost = ? WHERE id = ?";

    /**
     * Message, that is putted in Exception if there are select basket by user id problem
     */
    private static final String MESSAGE_SELECT_BASKET_PROBLEM = "Can't handle BasketDao.findByUserId request";

    /**
     * Message, that is putted in Exception if there are create basket problem
     */
    private static final String MESSAGE_INSERT_BASKET_PROBLEM = "Can't handle BasketDao.createBasket request";

    /**
     * Message, that is putted in Exception if there are set total cost problem
     */
    private static final String MESSAGE_SET_TOTAL_COST_PROBLEM = "Can't handle BasketDao.updateBasket request";

    /**
     * Message, that is putted in Exception if there are find by id problem
     */
    private static final String MESSAGE_FIND_BY_ID_PROBLEM = "Can't handle BasketDao.findById request";

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link BasketDaoImpl}
     */
    public static BasketDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private BasketDaoImpl() {
    }

    @Override
    public Basket findById(int id) throws DaoException {
        Basket basket = new Basket();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_ID)) {
            statement.setInt(FindBasketIndex.ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                basket.setId(resultSet.getInt(ColumnName.BASKET_ID));
                basket.setTotalCost(resultSet.getBigDecimal(ColumnName.BASKET_TOTAL_COST));
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_FIND_BY_ID_PROBLEM, e);
        }
        return basket;
    }

    @Override
    public Basket findByUserId(Integer userId) throws DaoException {
        Basket basket = new Basket();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_USER)) {
            statement.setInt(FindBasketIndex.ID, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                basket.setId(resultSet.getInt(ColumnName.BASKET_ID));
                basket.setTotalCost(resultSet.getBigDecimal(ColumnName.BASKET_TOTAL_COST));
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SELECT_BASKET_PROBLEM, e);
        }
        return basket;
    }

    @Override
    public Basket createBasket(Integer userId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BASKET_SQL)) {
            statement.setInt(FindBasketIndex.ID, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_INSERT_BASKET_PROBLEM, e);
        }
        return findByUserId(userId);
    }

    @Override
    public void updateBasket(Basket basket) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_TOTAL_COST)) {
            statement.setBigDecimal(SetCostIndex.TOTAL_COST, basket.getTotalCost());
            statement.setInt(SetCostIndex.ID, basket.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SET_TOTAL_COST_PROBLEM, e);
        }
    }

    /**
     * Static class that contains parameter indexes for getting basket data by user ID
     */
    private static class FindBasketIndex {
        private static final int ID = 1;
    }

    /**
     * Static class that contains parameter indexes for getting basket data by user ID
     */
    private static class SetCostIndex {
        private static final int TOTAL_COST = 1;
        private static final int ID = 2;
    }
}
