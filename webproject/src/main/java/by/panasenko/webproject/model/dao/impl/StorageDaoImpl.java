package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.StorageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.panasenko.webproject.model.dao.ColumnName.STORAGE_COUNT;
import static by.panasenko.webproject.model.dao.ColumnName.STORAGE_ID;

/**
 * Implementation of {@link StorageDao}. Provides methods to interact with Storage data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class StorageDaoImpl implements StorageDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final StorageDaoImpl instance = new StorageDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to get storage record by flower table */
    private static final String SELECT_STORAGE_BY_FLOWER = "SELECT storage_id, storage_count FROM storage st " +
            "JOIN flower fl ON fl.id = st.flowers_id " +
            "WHERE (flowers_id = ?)";

    /** Query for database to add storage */
    private static final String INSERT_STORAGE_SQL = "INSERT INTO storage (storage_count, flowers_id) VALUES (?,?)";

    /** Query for database to set storage count */
    private static final String SET_STORAGE_COUNT = "UPDATE storage SET storage_count = ? WHERE storage_id = ?";

    /** Query for database to set storage count by flower */
    private static final String SET_STORAGE_COUNT_BY_FLOWER = "UPDATE storage SET storage_count = ? WHERE flowers_id = ?";

    /**
     * Returns the instance of the class
     * @return Object of {@link StorageDaoImpl}
     */
    public static StorageDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private StorageDaoImpl() {
    }

    /**
     * Connects to database and returns {@link Storage} object by id of flower.
     *
     * @param flowerId is flower's id value.
     * @return {@link Storage} if storage data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public Storage findByFlowerId(Integer flowerId) throws DaoException {
        Storage storage = new Storage();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STORAGE_BY_FLOWER)) {
            statement.setInt(FindStorageIndex.ID, flowerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                storage.setId(resultSet.getInt(STORAGE_ID));
                storage.setCount(resultSet.getInt(STORAGE_COUNT));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle StorageDao.findByFlowerId request", e);
        }
        return storage;
    }

    /**
     * Connects to database and update storage data.
     *
     * @param storage is {@link Storage} object that contains all info about storage for update.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void updateStorage(Storage storage) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_STORAGE_COUNT)) {
            statement.setInt(SetStorageIndex.COUNT, storage.getCount());
            statement.setInt(SetStorageIndex.ID, storage.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle StorageDao.updateStorage request", e);
        }
    }

    /**
     * Connects to database and add new storage.
     *
     * @param storage is {@link Storage} object that contains all info about storage.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void insertStorage(Storage storage) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STORAGE_SQL)) {
            statement.setInt(InserStorage.COUNT, storage.getCount());
            statement.setInt(InserStorage.FLOWER_ID, storage.getFlower().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle StorageDao.insertStorage request", e);
        }
    }

    /**
     * Connects to database and set count to the storage by flower ID.
     *
     * @param flowerId is flower ID value.
     * @param count    is count of flower in storage
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public void updateStorageByFlower(Integer flowerId, Integer count) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_STORAGE_COUNT_BY_FLOWER)) {
            statement.setInt(SetStorageIndex.COUNT, count);
            statement.setInt(SetStorageIndex.ID, flowerId);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle StorageDao.updateStorageByFlower request", e);
        }
    }

    /**
     * Static class that contains parameter indexes for getting storage data by flower ID
     */
    private static class FindStorageIndex {
        private static final int ID = 1;
    }

    /**
     * Static class that contains parameter indexes for setting storage count
     */
    private static class SetStorageIndex {
        private static final int COUNT = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameter indexes for setting storage count
     */
    private static class InserStorage {
        private static final int COUNT = 1;
        private static final int FLOWER_ID = 2;
    }
}
