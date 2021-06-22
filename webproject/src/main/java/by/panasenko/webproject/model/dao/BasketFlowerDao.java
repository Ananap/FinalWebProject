package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface provides methods to interact with BasketFlower data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface BasketFlowerDao {

    /**
     * Connects to database and add new flower to basket.
     *
     * @param id       is basket ID value.
     * @param flowerId is flower ID value
     * @param count    is amount of the flower in basket
     * @param subTotal is sub total of user's basket, calculated as count of the flower multiplied by its price
     * @throws DaoException when problems with database connection occurs.
     */
    void addItemToBasket(Integer id, Integer flowerId, Integer count, BigDecimal subTotal) throws DaoException;

    /**
     * Connects to database and returns all info about basket by its ID.
     *
     * @param id is basket ID value.
     * @return List of {@link BasketFlower} with all matching data.
     * @throws DaoException when problems with database connection occurs.
     */
    List<BasketFlower> findByBasketId(Integer id) throws DaoException;

    /**
     * Connects to database and updates sub total of basket.
     *
     * @param basketFlower is {@link BasketFlower} object that contains all info about basket flower for update.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateSubTotal(BasketFlower basketFlower) throws DaoException;

    /**
     * Connects to database and updates count of flower in basket.
     *
     * @param basketFlower is {@link BasketFlower} object that contains all info about basket flower for update.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateCount(BasketFlower basketFlower) throws DaoException;

    /**
     * Connects to database and returns all info about basket flower by ID.
     *
     * @param id is basket flower ID value.
     * @return {@link BasketFlower} if basket flower data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    BasketFlower findById(Integer id) throws DaoException;

    /**
     * Connects to database and delete basket flower by ID.
     *
     * @param id is basket flower ID value
     * @throws DaoException when problems with database connection occurs.
     */
    void deleteBasketFlower(Integer id) throws DaoException;
}
