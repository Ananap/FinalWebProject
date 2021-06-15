package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.OrderDao;

import java.sql.*;

public class OrderDaoImpl implements OrderDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final OrderDaoImpl instance = new OrderDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to add order */
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (status_order, date_delivery, user_id, total_cost, time_order, address, date_order, cash) VALUES (?,?,?,?,?,?,?,?)";

    /** Message, that is putted in Exception if there are add item to basket problem */
    private static final String MESSAGE_INSERT_ORDER_PROBLEM = "Can't handle OrderDao.saveOrder request";

    /**
     * Returns the instance of the class
     * @return Object of {@link OrderDaoImpl}
     */
    public static OrderDaoImpl getInstance() {
        return instance;
    }

    /** Private constructor without parameters */
    private OrderDaoImpl() {
    }

    @Override
    public Order saveOrder(Order order) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            String status = String.valueOf(order.getStatusOrder());
            statement.setString(InsertOrderIndex.STATUS_ORDER, status);
            statement.setDate(InsertOrderIndex.DATE_DELIVERY, new java.sql.Date(order.getDateDelivery().getTime()));
            statement.setInt(InsertOrderIndex.USER_ID, order.getUser().getId());
            statement.setBigDecimal(InsertOrderIndex.TOTAL_COST, order.getTotalCost());
            statement.setString(InsertOrderIndex.TIME_ORDER, order.getTimeOrder());
            statement.setString(InsertOrderIndex.ADDRESS, order.getAddress());
            statement.setDate(InsertOrderIndex.DATE_ORDER, new java.sql.Date(order.getDateOrder().getTime()));
            statement.setBoolean(InsertOrderIndex.CASH, order.isCash());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating order failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_INSERT_ORDER_PROBLEM, e);
        }
        return order;
    }

    /**
     * Static class that contains parameter indexes for inserting order
     */
    private static class InsertOrderIndex {
        private static final int STATUS_ORDER = 1;
        private static final int DATE_DELIVERY = 2;
        private static final int USER_ID = 3;
        private static final int TOTAL_COST = 4;
        private static final int TIME_ORDER = 5;
        private static final int ADDRESS = 6;
        private static final int DATE_ORDER = 7;
        private static final int CASH = 8;
    }
}
