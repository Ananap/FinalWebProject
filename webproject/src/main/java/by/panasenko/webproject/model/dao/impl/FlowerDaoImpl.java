package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.*;
import by.panasenko.webproject.exception.DaoException;
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
     * Query for database to add flower
     */
    private static final String INSERT_FLOWER_SQL = "INSERT INTO flower (name, description, price, soil, origin, light, flower_type_id, watering) VALUES (?,?,?,?,?,?,?,?)";

    /**
     * Query for database to set flower image
     */
    private static final String SET_IMAGE_SQL = "UPDATE flower SET flower_image = ? WHERE id = ?";

    /**
     * Query for database to update flower
     */
    private static final String UPDATE_FLOWER_SQL = "UPDATE flower SET name = ?, description = ?, price = ?, soil = ?, origin = ?, light = ?, flower_type_id = ?, watering = ? WHERE id = ?";

    /**
     * Query for database to get record in flower table
     */
    private static final String SELECT_ALL_FLOWER_SQL = "SELECT id, name, description, price, flower_image FROM flower";

    /**
     * Query for database to get all record in flower table
     */
    private static final String SELECT_ALL_FLOWER_LIST_SQL = "SELECT id, name, description, price, flower_image, soil, origin, light, watering, type_id, category, type_description, storage_count FROM flower flowers " +
            "JOIN flower_type type ON flowers.flower_type_id = type.type_id " +
            "JOIN storage s ON flowers.id = s.flowers_id ";

    /**
     * Query for database to get flower by category
     */
    private static final String FIND_FLOWER_BY_CATEGORY = "SELECT id, name, description, price, flower_image FROM flower flowers " +
            "JOIN flower_type type ON flowers.flower_type_id = type.type_id " +
            "WHERE (flower_type_id = ?)";

    /**
     * Query for database to get flower by id
     */
    private static final String FIND_FLOWER_BY_ID = "SELECT id, name, description, price, flower_image, soil, origin, light, watering, type_id, category, type_description, storage_count FROM flower flowers " +
            "JOIN flower_type type ON flowers.flower_type_id = type.type_id " +
            "JOIN storage s ON flowers.id = s.flowers_id " +
            "WHERE (id = ?)";

    /**
     * Message, that is putted in Exception if there are select flower problem
     */
    private static final String MESSAGE_SELECT_FLOWERS_PROBLEM = "Can't handle FlowerDao.findAll request";

    /**
     * Message, that is putted in Exception if there are select flower by category problem
     */
    private static final String MESSAGE_SELECT_FLOWER_BY_CATEGORY_PROBLEM = "Can't handle FlowerDao.findByCategory request";

    /**
     * Message, that is putted in Exception if there are insert flower problem
     */
    private static final String MESSAGE_INSERT_FLOWER_PROBLEM = "Can't handle FlowerDao.createFlower request";

    /**
     * Message, that is putted in Exception if there are update flower image problem
     */
    private static final String MESSAGE_UPDATE_IMAGE_PROBLEM = "Can't handle FlowerDao.updateFlowerImage request";

    /**
     * Message, that is putted in Exception if there are select by id flower problem
     */
    private static final String MESSAGE_SELECT_BY_ID_PROBLEM = "Can't handle FlowerDao.findById request";

    /**
     * Message, that is putted in Exception if there are select all flower list problem
     */
    private static final String MESSAGE_SELECT_FLOWER_LIST_PROBLEM = "Can't handle FlowerDao.findAllFlowerList request";

    /**
     * Message, that is putted in Exception if there are update flower problem
     */
    private static final String MESSAGE_UPDATE_FLOWER_PROBLEM = "Can't handle FlowerDao.updateFlower request";

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
    public Flower createFlower(Flower flower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_FLOWER_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FlowerIndex.NAME, flower.getName());
            statement.setString(FlowerIndex.DESCRIPTION, flower.getDescription());
            statement.setDouble(FlowerIndex.PRICE, flower.getPrice());
            statement.setString(FlowerIndex.SOIL, String.valueOf(flower.getSoil()));
            statement.setString(FlowerIndex.ORIGIN, flower.getOriginCountry());
            statement.setBoolean(FlowerIndex.LIGHT, flower.isLight());
            statement.setInt(FlowerIndex.TYPE_ID, flower.getFlowerType().getId());
            statement.setInt(FlowerIndex.WATERING, flower.getWatering());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating flower failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    flower.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Creating flower failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_INSERT_FLOWER_PROBLEM, e);
        }
        return flower;
    }

    @Override
    public List<Flower> findAll() throws DaoException {
        List<Flower> flowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FLOWER_SQL);
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
            throw new DaoException(MESSAGE_SELECT_FLOWERS_PROBLEM, e);
        }
        return flowerList;
    }

    @Override
    public List<Flower> findByCategory(String category) throws DaoException {
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
            throw new DaoException(MESSAGE_SELECT_FLOWER_BY_CATEGORY_PROBLEM, e);
        }
        return flowerList;
    }

    @Override
    public Flower findById(String flowerId) throws DaoException {
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
                flower.setOriginCountry(resultSet.getString(ColumnName.FLOWER_COUNTRY));
                flower.setLight(resultSet.getBoolean(ColumnName.FLOWER_LIGHT));
                flower.setWatering(resultSet.getInt(ColumnName.FLOWER_WATERING));
                FlowerType flowerType = new FlowerType();
                flowerType.setId(resultSet.getInt(ColumnName.FLOWER_TYPE_ID));
                FlowerCategory flowerCategory = FlowerCategory.valueOf(resultSet.getString(ColumnName.FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(flowerCategory);
                flowerType.setDescription(resultSet.getString(ColumnName.FLOWER_TYPE_DESCRIPTION));
                flower.setFlowerType(flowerType);
                Soil soil = Soil.valueOf(resultSet.getString(ColumnName.SOIL));
                flower.setSoil(soil);
                Storage storage = new Storage();
                storage.setCount(resultSet.getInt(ColumnName.STORAGE_COUNT));
                flower.setStorage(storage);
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SELECT_BY_ID_PROBLEM, e);
        }
        return flower;
    }

    @Override
    public void updateFlowerImage(Flower dbFlower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_IMAGE_SQL)) {
            statement.setString(UpdateImageIndex.IMAGE, dbFlower.getFlowerImage());
            statement.setInt(UpdateImageIndex.ID, dbFlower.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_UPDATE_IMAGE_PROBLEM, e);
        }
    }

    @Override
    public List<Flower> findAllFlowerList() throws DaoException {
        List<Flower> flowerList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FLOWER_LIST_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flower flower = new Flower();
                flower.setId(resultSet.getInt(ColumnName.FLOWER_ID));
                flower.setName(resultSet.getString(ColumnName.FLOWER_NAME));
                flower.setDescription(resultSet.getString(ColumnName.FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(ColumnName.FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(ColumnName.FLOWER_IMAGE));
                flower.setOriginCountry(resultSet.getString(ColumnName.FLOWER_COUNTRY));
                flower.setLight(resultSet.getBoolean(ColumnName.FLOWER_LIGHT));
                flower.setWatering(resultSet.getInt(ColumnName.FLOWER_WATERING));
                FlowerType flowerType = new FlowerType();
                flowerType.setId(resultSet.getInt(ColumnName.FLOWER_TYPE_ID));
                FlowerCategory flowerCategory = FlowerCategory.valueOf(resultSet.getString(ColumnName.FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(flowerCategory);
                flowerType.setDescription(resultSet.getString(ColumnName.FLOWER_TYPE_DESCRIPTION));
                flower.setFlowerType(flowerType);
                Soil soil = Soil.valueOf(resultSet.getString(ColumnName.SOIL));
                flower.setSoil(soil);
                Storage storage = new Storage();
                storage.setCount(resultSet.getInt(ColumnName.STORAGE_COUNT));
                flower.setStorage(storage);
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SELECT_FLOWER_LIST_PROBLEM, e);
        }
        return flowerList;
    }

    @Override
    public void updateFlower(int id, Flower flower) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FLOWER_SQL)) {
            statement.setString(FlowerIndex.NAME, flower.getName());
            statement.setString(FlowerIndex.DESCRIPTION, flower.getDescription());
            statement.setDouble(FlowerIndex.PRICE, flower.getPrice());
            statement.setString(FlowerIndex.SOIL, String.valueOf(flower.getSoil()));
            statement.setString(FlowerIndex.ORIGIN, flower.getOriginCountry());
            statement.setBoolean(FlowerIndex.LIGHT, flower.isLight());
            statement.setInt(FlowerIndex.TYPE_ID, flower.getFlowerType().getId());
            statement.setInt(FlowerIndex.WATERING, flower.getWatering());
            statement.setInt(FlowerIndex.ID, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_UPDATE_FLOWER_PROBLEM, e);
        }
    }

    @Override
    public void deleteById(int id) throws DaoException {

    }

    /**
     * Static class that contains parameter indexes for getting flower data by flowerType ID
     */
    private static class FindFlowerIndex {
        private static final int INDEX = 1;
    }

    /**
     * Static class that contains parameter indexes for updating flower image
     */
    private static class UpdateImageIndex {
        private static final int IMAGE = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameter indexes for inserting flower data
     */
    private static class FlowerIndex {
        private static final int NAME = 1;
        private static final int DESCRIPTION = 2;
        private static final int PRICE = 3;
        private static final int SOIL = 4;
        private static final int ORIGIN = 5;
        private static final int LIGHT = 6;
        private static final int TYPE_ID = 7;
        private static final int WATERING = 8;
        private static final int ID = 9;
    }
}
