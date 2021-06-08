package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.exception.DAOException;
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
     * Query for database to create basket
     */
    private static final String INSERT_BASKET_SQL = "INSERT INTO basket (user_id_foreign) VALUE (?)";

    /**
     * Message, that is putted in Exception if there are select basket by user id problem
     */
    private static final String MESSAGE_SELECT_BASKET_PROBLEM = "Can't handle BasketDao.findByUserId request";

    /**
     * Message, that is putted in Exception if there are create basket problem
     */
    private static final String MESSAGE_INSERT_BASKET_PROBLEM = "Can't handle BasketDao.createBasket request";

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
    public Basket findByUserId(Integer id) throws DAOException {
        Basket basket = new Basket();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_USER)) {
            statement.setInt(FindBasketIndex.ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                basket.setId(resultSet.getInt(ColumnName.BASKET_ID));
                basket.setTotalCost(resultSet.getBigDecimal(ColumnName.BASKET_TOTAL_COST));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SELECT_BASKET_PROBLEM, e);
        }
        return basket;
    }

    @Override
    public Basket createBasket(Integer id) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_BASKET_SQL)) {
            ps.setInt(FindBasketIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_INSERT_BASKET_PROBLEM, e);
        }
        return findByUserId(id);
    }

    @Override
    public void updateBasket(Basket basket) throws DAOException {

    }

    /**
     * Static class that contains parameter indexes for getting basket data by user ID
     */
    private static class FindBasketIndex {
        private static final int ID = 1;
    }
}
