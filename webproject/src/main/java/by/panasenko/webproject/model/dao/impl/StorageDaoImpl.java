package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.ColumnName;
import by.panasenko.webproject.model.dao.StorageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageDaoImpl implements StorageDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final StorageDaoImpl instance = new StorageDaoImpl();

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Query for database to get storage record by flower table
     */
    private static final String SELECT_STORAGE_BY_FLOWER = "SELECT storage_id, storage_count FROM storage st " +
            "JOIN flower fl ON fl.id = st.flowers_id " +
            "WHERE (flowers_id = ?)";

    /**
     * Message, that is putted in Exception if there are select storage problem
     */
    private static final String MESSAGE_SELECT_STORAGE_PROBLEM = "Can't handle StorageDao.findByFlowerId request";

    /**
     * Returns the instance of the class
     *
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

    @Override
    public Storage findByFlowerId(String flowerId) throws DAOException {
        Storage storage = new Storage();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STORAGE_BY_FLOWER)) {
            statement.setInt(FindStorageIndex.ID, Integer.parseInt(flowerId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                storage.setId(resultSet.getInt(ColumnName.STORAGE_ID));
                storage.setCount(resultSet.getInt(ColumnName.STORAGE_COUNT));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SELECT_STORAGE_PROBLEM, e);
        }
        return storage;
    }

    /**
     * Static class that contains parameter indexes for getting storage data by flower ID
     */
    private static class FindStorageIndex {
        private static final int ID = 1;
    }
}
