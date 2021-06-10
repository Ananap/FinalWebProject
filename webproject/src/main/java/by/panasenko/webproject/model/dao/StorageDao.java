package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;

public interface StorageDao {
    Storage findByFlowerId(String flowerId) throws DaoException;
}
