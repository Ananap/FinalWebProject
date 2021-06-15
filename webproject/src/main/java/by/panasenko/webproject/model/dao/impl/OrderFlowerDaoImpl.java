package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.OrderFlower;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.OrderFlowerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderFlowerDaoImpl implements OrderFlowerDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final OrderFlowerDaoImpl instance = new OrderFlowerDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to add order */
    private static final String INSERT_ORDER_FLOWER_SQL = "INSERT INTO order_flower (flower_id, count, order_id, sub_total) VALUES (?,?,?,?)";

    /** Message, that is putted in Exception if there are add item to basket problem */
    private static final String MESSAGE_INSERT_ORDER_FLOWER_PROBLEM = "Can't handle OrderFlowerDao.saveOrderFlower request";

    /**
     * Returns the instance of the class
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
            throw new DaoException(MESSAGE_INSERT_ORDER_FLOWER_PROBLEM, e);
        }
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
}
