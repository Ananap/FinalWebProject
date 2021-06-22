package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.entity.Status;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.OrderDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.panasenko.webproject.model.dao.ColumnName.*;

/**
 * Implementation of {@link OrderDao}. Provides methods to interact with Order data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class OrderDaoImpl implements OrderDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final OrderDaoImpl instance = new OrderDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to add order */
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (status_order, date_delivery, user_id, total_cost, time_order, address, date_order, cash) VALUES (?,?,?,?,?,?,?,?)";

    /** Query for database to select order by user */
    private static final String SELECT_ORDER_BY_USER_SQL = "SELECT id, status_order, date_order, total_cost FROM orders WHERE orders.user_id = ?";

    /** Query for database to select all order data */
    private static final String SELECT_ORDERS_SQL = "SELECT id, address, cash, date_delivery, date_order, status_order FROM orders";

    /** Query for database to select order data by id */
    private static final String SELECT_ORDER_BY_ID_SQL = "SELECT id, status_order, total_cost FROM orders WHERE id = ?";

    /** Query for database to select order data by id */
    private static final String UPDATE_STATUS_SQL = "UPDATE orders SET status_order = ? WHERE id = ?";

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link OrderDaoImpl}
     */
    public static OrderDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private OrderDaoImpl() {
    }

    /**
     * Connects to database and add new order.
     *
     * @param order is {@link Order} object that contains all info about order.
     * @return {@link Order} object that was saved in database
     * @throws DaoException when problems with database connection occurs.
     */
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
            throw new DaoException("Can't handle OrderDao.saveOrder request", e);
        }
        return order;
    }

    /**
     * Connects to database and return list of orders that linked to the user by ID.
     *
     * @param id is user ID
     * @return List of {@link Order} with all matching orders.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public List<Order> findByUserId(Integer id) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_USER_SQL)) {
            statement.setInt(FindOrder.ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(ORDER_ID));
                Status status = Status.valueOf(resultSet.getString(ORDER_STATUS));
                order.setStatusOrder(status);
                order.setDateOrder(resultSet.getDate(ORDER_DATE));
                order.setTotalCost(resultSet.getBigDecimal(ORDER_TOTAL_COST));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderDao.findByUserId request", e);
        }
        return orderList;
    }

    /**
     * Connects to database and returns list of all orders.
     *
     * @return List of {@link Order} with all orders.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(ORDER_ID));
                order.setAddress(resultSet.getString(ORDER_ADDRESS));
                order.setCash(resultSet.getBoolean(ORDER_CASH));
                order.setDateDelivery(resultSet.getDate(ORDER_DATE_DELIVERY));
                order.setDateOrder(resultSet.getDate(ORDER_DATE));
                Status status = Status.valueOf(resultSet.getString(ORDER_STATUS));
                order.setStatusOrder(status);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderDao.findAll request", e);
        }
        return orderList;
    }

    /**
     * Connects to database and returns all info about order by ID.
     *
     * @param id is order ID value.
     * @return {@link Order} if order's data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public Order findById(Integer id) throws DaoException {
        Order order = new Order();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID_SQL)) {
            statement.setInt(FindOrder.ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order.setId(resultSet.getInt(ORDER_ID));
                Status status = Status.valueOf(resultSet.getString(ORDER_STATUS));
                order.setStatusOrder(status);
                order.setTotalCost(resultSet.getBigDecimal(ORDER_TOTAL_COST));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderDao.findById request", e);
        }
        return order;
    }

    /**
     * Connects to database and set order status to the order by ID.
     *
     * @param orderId     is order ID value
     * @param orderStatus is text that contains order's status.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void updateStatusById(String orderStatus, Integer orderId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_SQL)) {
            statement.setString(UpdateStatusIndex.STATUS, orderStatus);
            statement.setInt(UpdateStatusIndex.ID, orderId);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderDao.updateStatusById request", e);
        }
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

    /**
     * Static class that contains parameter indexes for getting order data by user id
     */
    private static class FindOrder {
        private static final int ID = 1;
    }

    /**
     * Static class that contains parameter indexes for updating status order
     */
    private static class UpdateStatusIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }
}
