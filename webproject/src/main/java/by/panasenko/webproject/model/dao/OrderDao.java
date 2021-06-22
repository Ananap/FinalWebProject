package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

/**
 * Interface provides methods to interact with Order data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface OrderDao {
    /**
     * Connects to database and add new order.
     *
     * @param order is {@link Order} object that contains all info about order.
     * @return {@link Order} object that was saved in database
     * @throws DaoException when problems with database connection occurs.
     */
    Order saveOrder(Order order) throws DaoException;

    /**
     * Connects to database and return list of orders that linked to the user by ID.
     *
     * @param id is user ID
     * @return List of {@link Order} with all matching orders.
     * @throws DaoException when problems with database connection occurs.
     */
    List<Order> findByUserId(Integer id) throws DaoException;

    /**
     * Connects to database and returns list of all orders.
     *
     * @return List of {@link Order} with all orders.
     * @throws DaoException when problems with database connection occurs.
     */
    List<Order> findAll() throws DaoException;

    /**
     * Connects to database and returns all info about order by ID.
     *
     * @param id is order ID value.
     * @return {@link Order} if order's data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    Order findById(Integer id) throws DaoException;

    /**
     * Connects to database and set order status to the order by ID.
     *
     * @param orderId     is order ID value
     * @param orderStatus is text that contains order's status.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateStatusById(String orderStatus, Integer orderId) throws DaoException;
}
