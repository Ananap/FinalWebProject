package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.FlowerCategory;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.FlowerTypeDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.panasenko.webproject.model.dao.ColumnName.*;

/**
 * Implementation of {@link FlowerTypeDao}. Provides methods to interact with FlowerType data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class FlowerTypeDaoImpl implements FlowerTypeDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final FlowerTypeDaoImpl instance = new FlowerTypeDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to get all record in flower_type table */
    private static final String SELECT_ALL_FLOWER_TYPE_SQL = "SELECT type_id, category, type_description FROM flower_type";

    /**Query for database to get all record in flower_type table*/
    private static final String SELECT_FLOWER_TYPE_BY_ID = "SELECT type_id, category, type_description FROM flower_type " +
            "WHERE (type_id = ?)";

    /**
     * Returns the instance of the class
     * @return Object of {@link FlowerTypeDaoImpl}
     */
    public static FlowerTypeDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private FlowerTypeDaoImpl() {
    }

    /**
     * Connects to database and returns list of all flower types.
     *
     * @return List of {@link FlowerType} with all type of flowers.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public List<FlowerType> findAll() throws DaoException {
        List<FlowerType> flowerTypeList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FLOWER_TYPE_SQL);
            while (resultSet.next()) {
                FlowerType flowerType = new FlowerType();
                flowerType.setId(resultSet.getInt(FLOWER_TYPE_ID));
                FlowerCategory category = FlowerCategory.valueOf(resultSet.getString(FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(category);
                flowerType.setDescription(resultSet.getString(FLOWER_TYPE_DESCRIPTION));
                flowerTypeList.add(flowerType);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerTypeDao.findAll request", e);
        }
        return flowerTypeList;
    }

    /**
     * Connects to database and returns all info about flower type by ID.
     *
     * @param id is type of flower ID value.
     * @return {@link FlowerType} if type of flower data found, null if not.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public FlowerType findById(Integer id) throws DaoException {
        FlowerType flowerType = new FlowerType();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FLOWER_TYPE_BY_ID)) {
            statement.setInt(FindFlowerTypeIndexID.ID, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flowerType.setId(resultSet.getInt(FLOWER_TYPE_ID));
                FlowerCategory flowerCategory = FlowerCategory.valueOf(resultSet.getString(FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(flowerCategory);
                flowerType.setDescription(resultSet.getString(FLOWER_TYPE_DESCRIPTION));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerTypeDao.findById request", e);
        }
        return flowerType;
    }

    /**
     * Static class that contains parameter indexes for getting flower type data by flowerType ID
     */
    private static class FindFlowerTypeIndexID {
        private static final int ID = 1;
    }
}
