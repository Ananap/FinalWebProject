package by.panasenko.webproject.model.dao;


import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.DAOException;

import java.util.List;

/**
 * Interface provides methods to interact with Flower data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface FlowerDao {
    List<Flower> findAll() throws DAOException;

    List<Flower> findByCategory(String category) throws DAOException;

    Flower findById(String flowerId) throws DAOException;
}
