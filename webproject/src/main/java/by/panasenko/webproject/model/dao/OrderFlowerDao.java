package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.OrderFlower;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

/**
 * Interface provides methods to interact with OrderFlower data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface OrderFlowerDao {
    /**
     * Connects to database and add new orderFlower.
     *
     * @param orderFlower is {@link OrderFlower} object that contains all info about orderFlower.
     * @throws DaoException when problems with database connection occurs.
     */
    void saveOrderFlower(OrderFlower orderFlower) throws DaoException;

    /**
     * Connects to database and return list of orderFlowers that linked to the order by ID.
     *
     * @param id is order ID
     * @return List of {@link OrderFlower} with all matching data.
     * @throws DaoException when problems with database connection occurs.
     */
    List<OrderFlower> findByOrder(Integer id) throws DaoException;
}
