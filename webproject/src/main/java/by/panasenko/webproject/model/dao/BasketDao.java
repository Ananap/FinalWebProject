package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.exception.DaoException;

/**
 * Interface provides methods to interact with BasketDao data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface BasketDao {

    /**
     * Connects to database and returns basket that linked to the user by ID.
     *
     * @param id is user ID
     * @return {@link Basket} if basket's data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    Basket findByUserId(Integer id) throws DaoException;

    /**
     * Connects to database and add new basket.
     *
     * @param id is user ID value.
     * @return {@link Basket} object that was saved in database
     * @throws DaoException when problems with database connection occurs.
     */
    Basket createBasket(Integer id) throws DaoException;

    /**
     * Connects to database and updates flower's data by ID.
     *
     * @param basket is {@link Basket} object that contains all info about basket for update.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateBasket(Basket basket) throws DaoException;

    /**
     * Connects to database and returns basket by ID.
     *
     * @param id is basket ID value.
     * @return {@link Basket} if basket data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    Basket findById(Integer id) throws DaoException;
}
