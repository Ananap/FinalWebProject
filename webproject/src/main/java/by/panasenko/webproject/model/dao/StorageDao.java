package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;

/**
 * Interface provides methods to interact with Storage data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface StorageDao {
    Storage findByFlowerId(Integer flowerId) throws DaoException;
    void updateStorage(Storage storage) throws DaoException;
    void insertStorage(Storage storage) throws DaoException;
    void updateStorageByFlower(Integer flowerId, Integer count) throws DaoException;
}
