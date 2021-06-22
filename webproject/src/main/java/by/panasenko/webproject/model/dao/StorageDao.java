package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;

/**
 * Interface provides methods to interact with Storage data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface StorageDao {

    /**
     * Connects to database and returns {@link Storage} object by id of flower.
     *
     * @param flowerId is flower's id value.
     * @return {@link Storage} if storage data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    Storage findByFlowerId(Integer flowerId) throws DaoException;

    /**
     * Connects to database and update storage data.
     *
     * @param storage is {@link Storage} object that contains all info about storage for update.
     * @throws DaoException when problems with database connection occurs.
     */
    void updateStorage(Storage storage) throws DaoException;

    /**
     * Connects to database and add new storage.
     *
     * @param storage is {@link Storage} object that contains all info about storage.
     * @throws DaoException when problems with database connection occurs.
     */
    void insertStorage(Storage storage) throws DaoException;

    /**
     * Connects to database and set count to the storage by flower ID.
     *
     * @param flowerId is flower ID value.
     * @param count    is count of flower in storage
     * @throws DaoException when problems with database connection occurs.
     */
    void updateStorageByFlower(Integer flowerId, Integer count) throws DaoException;
}
