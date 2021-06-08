package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.DAOException;

import java.util.List;

/**
 * Interface provides methods to interact with FlowerType data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface FlowerTypeDao {
    List<FlowerType> findAll() throws DAOException;

    FlowerType findById(String category) throws DAOException;
}
