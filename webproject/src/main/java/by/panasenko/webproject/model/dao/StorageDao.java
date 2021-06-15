package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;

// TODO javaDoc in all Dao
public interface StorageDao {
    Storage findByFlowerId(Integer flowerId) throws DaoException;
    void updateStorage(Storage storage) throws DaoException;
    void insertStorage(Storage storage) throws DaoException;
    void updateStorageByFlower(Integer flowerId, Integer count) throws DaoException;
}
