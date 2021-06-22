package by.panasenko.webproject.model.dao;


import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

/**
 * Interface provides methods to interact with Flower data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface FlowerDao {

    /**
     * Connects to database and returns list of all flowers.
     *
     * @return List of {@link Flower} with all flowers.
     * @throws DaoException when problems with database connection occurs.
     */
    List<Flower> findAll() throws DaoException;

    /**
     * Connects to database and returns all info about flower by its category.
     *
     * @param category is text that contains category of flower.
     * @return List of {@link Flower} with all matching flowers.
     * @throws DaoException when problems with database connection occurs.
     */
    List<Flower> findByCategory(String category) throws DaoException;

    /**
     * Connects to database and returns all info about flower by ID.
     *
     * @param flowerId is flower ID value.
     * @return {@link Flower} if flower data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    Flower findById(Integer flowerId) throws DaoException;

    /**
     * Connects to database and add new flower.
     *
     * @param flower is {@link Flower} object that contains all info about flower.
     * @return {@link Flower} object that was saved in database
     * @throws DaoException when problems with database connection occurs.
     */
    Flower createFlower(Flower flower) throws DaoException;

    /**
     * Connects to database and update flower image.
     *
     * @param dbFlower is {@link Flower} object that contains all info about flower for update.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateFlowerImage(Flower dbFlower) throws DaoException;

    /**
     * Connects to database and returns list of all flowers.
     *
     * @return List of {@link Flower} with all flower's detailed data.
     * @throws DaoException when problems with database connection occurs.
     */
    List<Flower> findAllFlowerList() throws DaoException;

    /**
     * Connects to database and updates flower's data by ID.
     *
     * @param id     is flower ID value
     * @param flower is {@link Flower} object that contains all info about flower for update.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateFlower(Integer id, Flower flower) throws DaoException;

    /**
     * Connects to database and delete flower by ID.
     *
     * @param id is flower ID value
     * @throws DaoException when problems with database connection occurs.
     */
    void deleteById(Integer id) throws DaoException;
}
