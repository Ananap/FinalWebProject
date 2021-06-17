package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.OrderFlower;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.OrderFlowerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.panasenko.webproject.model.dao.ColumnName.*;

/**
 * Implementation of {@link OrderFlowerDao}. Provides methods to interact with OrderFlower data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class OrderFlowerDaoImpl implements OrderFlowerDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final OrderFlowerDaoImpl instance = new OrderFlowerDaoImpl();

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Query for database to add order flower
     */
    private static final String INSERT_ORDER_FLOWER_SQL = "INSERT INTO order_flower (flower_id, count, order_id, sub_total) VALUES (?,?,?,?)";

    /**
     * Query for database to select order flower data by order id
     */
    private static final String SELECT_ORDER_FLOWER_SQL = "SELECT name, price, count, sub_total FROM order_flower o " +
            "JOIN flower f ON o.flower_id = f.id " +
            "WHERE order_id = ?";

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link OrderFlowerDaoImpl}
     */
    public static OrderFlowerDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private OrderFlowerDaoImpl() {
    }

    @Override
    public void saveOrderFlower(OrderFlower orderFlower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_FLOWER_SQL)) {
            statement.setInt(InsertOrderFlowerIndex.FLOWER_ID, orderFlower.getFlower().getId());
            statement.setInt(InsertOrderFlowerIndex.COUNT, orderFlower.getCount());
            statement.setInt(InsertOrderFlowerIndex.ORDER_ID, orderFlower.getOrder().getId());
            statement.setBigDecimal(InsertOrderFlowerIndex.SUB_TOTAL, orderFlower.getSubTotal());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderFlowerDao.saveOrderFlower request", e);
        }
    }

    @Override
    public List<OrderFlower> findByOrder(int id) throws DaoException {
        List<OrderFlower> orderFlowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_FLOWER_SQL)) {
            statement.setInt(SelectOrderFlowerIndex.ORDER_ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderFlower orderFlower = new OrderFlower();
                Flower flower = new Flower();
                flower.setName(resultSet.getString(FLOWER_NAME));
                flower.setPrice(resultSet.getDouble(FLOWER_PRICE));
                orderFlower.setCount(resultSet.getInt(ORDER_FLOWER_COUNT));
                orderFlower.setSubTotal(resultSet.getBigDecimal(ORDER_FLOWER_SUB_TOTAL));
                orderFlower.setFlower(flower);
                orderFlowerList.add(orderFlower);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderFlowerDao.findByOrder request", e);
        }
        return orderFlowerList;
    }

    /**
     * Static class that contains parameter indexes for inserting orderFlower
     */
    private static class InsertOrderFlowerIndex {
        private static final int FLOWER_ID = 1;
        private static final int COUNT = 2;
        private static final int ORDER_ID = 3;
        private static final int SUB_TOTAL = 4;
    }

    /**
     * Static class that contains parameter indexes for selecting orderFlower
     */
    private static class SelectOrderFlowerIndex {
        private static final int ORDER_ID = 1;
    }
}
