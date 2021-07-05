package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

/**
 * Interface provides methods to interact with FlowerType data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface FlowerTypeDao {

    /**
     * Connects to database and returns list of all flower types.
     *
     * @return List of {@link FlowerType} with all type of flowers.
     * @throws DaoException when problems with database connection occurs.
     */
    List<FlowerType> findAll() throws DaoException;

    /**
     * Connects to database and returns all info about flower type by ID.
     *
     * @param id is type of flower ID value.
     * @return {@link FlowerType} if type of flower data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    FlowerType findById(Integer id) throws DaoException;
}
