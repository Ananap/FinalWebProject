package by.panasenko.webproject.model.dao;


import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

/**
 * Interface provides methods to interact with Flower data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface FlowerDao {
    List<Flower> findAll() throws DaoException;

    List<Flower> findByCategory(String category) throws DaoException;

    Flower findById(String flowerId) throws DaoException;
}
