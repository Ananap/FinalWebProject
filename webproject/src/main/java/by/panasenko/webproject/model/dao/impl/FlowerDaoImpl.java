package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.FlowerCategory;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.entity.Soil;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.ColumnName;
import by.panasenko.webproject.model.dao.FlowerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerDaoImpl implements FlowerDao {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final FlowerDaoImpl instance = new FlowerDaoImpl();

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Query for database to get all record in flower_type table
     */
    private static final String SELECT_ALL_FLOWER_TYPE_SQL = "SELECT id, name, description, price, flower_image FROM flower";

    /**
     * Query for database to get flower by category
     */
    private static final String FIND_FLOWER_BY_CATEGORY = "SELECT id, name, description, price, flower_image FROM flower flowers " +
            "JOIN flower_type type ON flowers.flower_type_id = type.type_id " +
            "WHERE (flower_type_id = ?)";

    /**
     * Query for database to get flower by id
     */
    private static final String FIND_FLOWER_BY_ID = "SELECT id, name, description, price, flower_image, soil, origin, light, watering, type_id, category, type_description FROM flower flowers " +
            "JOIN flower_type type ON flowers.flower_type_id = type.type_id " +
            "WHERE (id = ?)";

    /**
     * Message, that is putted in Exception if there are select flower problem
     */
    private static final String MESSAGE_SELECT_FLOWERS_PROBLEM = "Can't handle FlowerDao.findAll request";

    /**
     * Message, that is putted in Exception if there are select flower by category problem
     */
    private static final String MESSAGE_SELECT_FLOWER_BY_CATEGORY = "Can't handle FlowerDao.findByCategory request";

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link FlowerDaoImpl}
     */
    public static FlowerDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private FlowerDaoImpl() {
    }

    @Override
    public List<Flower> findAll() throws DAOException {
        List<Flower> flowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FLOWER_TYPE_SQL);
            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt(ColumnName.FLOWER_ID));
                flower.setName(resultSet.getString(ColumnName.FLOWER_NAME));
                flower.setDescription(resultSet.getString(ColumnName.FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(ColumnName.FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(ColumnName.FLOWER_IMAGE));
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SELECT_FLOWERS_PROBLEM, e);
        }
        return flowerList;
    }

    @Override
    public List<Flower> findByCategory(String category) throws DAOException {
        List<Flower> flowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FLOWER_BY_CATEGORY)) {
            statement.setString(FindFlowerIndex.INDEX, category);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt(ColumnName.FLOWER_ID));
                flower.setName(resultSet.getString(ColumnName.FLOWER_NAME));
                flower.setDescription(resultSet.getString(ColumnName.FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(ColumnName.FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(ColumnName.FLOWER_IMAGE));
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SELECT_FLOWER_BY_CATEGORY, e);
        }
        return flowerList;
    }

    @Override
    public Flower findById(String flowerId) throws DAOException {
        Flower flower = new Flower();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FLOWER_BY_ID)) {
            statement.setInt(FindFlowerIndex.INDEX, Integer.parseInt(flowerId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flower.setId(resultSet.getInt(ColumnName.FLOWER_ID));
                flower.setName(resultSet.getString(ColumnName.FLOWER_NAME));
                flower.setDescription(resultSet.getString(ColumnName.FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(ColumnName.FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(ColumnName.FLOWER_IMAGE));
                Soil soil = Soil.valueOf(resultSet.getString(ColumnName.FLOWER_SOIL));
                flower.setSoil(soil);
                flower.setOriginCountry(resultSet.getString(ColumnName.FLOWER_COUNTRY));
                flower.setLight(resultSet.getBoolean(ColumnName.FLOWER_LIGHT));
                flower.setWatering(resultSet.getInt(ColumnName.FLOWER_WATERING));
                FlowerType flowerType = new FlowerType();
                flowerType.setId(resultSet.getInt(ColumnName.FLOWER_TYPE_ID));
                FlowerCategory flowerCategory = FlowerCategory.valueOf(resultSet.getString(ColumnName.FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(flowerCategory);
                flowerType.setDescription(resultSet.getString(ColumnName.FLOWER_TYPE_DESCRIPTION));
                flower.setFlowerType(flowerType);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SELECT_FLOWER_BY_CATEGORY, e);
        }
        return flower;
    }

    /**
     * Static class that contains parameter indexes for getting flower data by flowerType ID
     */
    private static class FindFlowerIndex {
        private static final int INDEX = 1;
    }
}
