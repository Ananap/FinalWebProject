package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.BasketDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.panasenko.webproject.model.dao.ColumnName.BASKET_ID;
import static by.panasenko.webproject.model.dao.ColumnName.BASKET_TOTAL_COST;

/**
 * Implementation of {@link BasketDao}. Provides methods to interact with Basket data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class BasketDaoImpl implements BasketDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final BasketDaoImpl instance = new BasketDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to get basket by user id */
    private static final String FIND_BASKET_BY_USER = "SELECT id, user_id_foreign, total_cost FROM basket b " +
            "JOIN users u ON b.user_id_foreign = u.user_id " +
            "WHERE (user_id_foreign = ?)";

    /** Query for database to get basket by id */
    private static final String FIND_BASKET_BY_ID = "SELECT id, total_cost FROM basket b " +
            "WHERE (id = ?)";

    /** Query for database to create basket */
    private static final String INSERT_BASKET_SQL = "INSERT INTO basket (user_id_foreign) VALUE (?)";

    /** Query for database to set total cost to basket */
    private static final String SET_TOTAL_COST = "UPDATE basket SET total_cost = ? WHERE id = ?";

    /**
     * Returns the instance of the class
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

    /**
     * Connects to database and returns basket by ID.
     *
     * @param id is basket ID value.
     * @return {@link Basket} if basket data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public Basket findById(Integer id) throws DaoException {
        Basket basket = new Basket();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_ID)) {
            statement.setInt(FindBasketIndex.ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                basket.setId(resultSet.getInt(BASKET_ID));
                basket.setTotalCost(resultSet.getBigDecimal(BASKET_TOTAL_COST));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketDao.findById request", e);
        }
        return basket;
    }

    /**
     * Connects to database and returns basket that linked to the user by ID.
     *
     * @param userId is user ID
     * @return {@link Basket} if basket's data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public Basket findByUserId(Integer userId) throws DaoException {
        Basket basket = new Basket();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BASKET_BY_USER)) {
            statement.setInt(FindBasketIndex.ID, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                basket.setId(resultSet.getInt(BASKET_ID));
                basket.setTotalCost(resultSet.getBigDecimal(BASKET_TOTAL_COST));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketDao.findByUserId request", e);
        }
        return basket;
    }

    /**
     * Connects to database and add new basket.
     *
     * @param userId is user ID value.
     * @return {@link Basket} object that was saved in database
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public Basket createBasket(Integer userId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BASKET_SQL)) {
            statement.setInt(FindBasketIndex.ID, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketDao.createBasket request", e);
        }
        return findByUserId(userId);
    }

    /**
     * Connects to database and updates flower's data by ID.
     *
     * @param basket is {@link Basket} object that contains all info about basket for update.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void updateBasket(Basket basket) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_TOTAL_COST)) {
            statement.setBigDecimal(SetCostIndex.TOTAL_COST, basket.getTotalCost());
            statement.setInt(SetCostIndex.ID, basket.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle BasketDao.updateBasket request", e);
        }
    }

    /** Static class that contains parameter indexes for getting basket data by user ID */
    private static class FindBasketIndex {
        private static final int ID = 1;
    }

    /** Static class that contains parameter indexes for getting basket data by user ID */
    private static class SetCostIndex {
        private static final int TOTAL_COST = 1;
        private static final int ID = 2;
    }
}
