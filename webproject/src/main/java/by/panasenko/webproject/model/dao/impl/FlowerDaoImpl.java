package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.*;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.FlowerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.panasenko.webproject.model.dao.ColumnName.*;

/**
 * Implementation of {@link FlowerDao}. Provides methods to interact with Flower data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
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
     * Query for database to delete flower
     */
    private static final String DELETE_FLOWER_SQL = "DELETE FROM flower WHERE id = ?";

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
            throw new DaoException("Can't handle FlowerDao.createFlower request", e);
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
                flower.setId(resultSet.getInt(FLOWER_ID));
                flower.setName(resultSet.getString(FLOWER_NAME));
                flower.setDescription(resultSet.getString(FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(FLOWER_IMAGE));
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerDao.findAll request", e);
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
                flower.setId(resultSet.getInt(FLOWER_ID));
                flower.setName(resultSet.getString(FLOWER_NAME));
                flower.setDescription(resultSet.getString(FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(FLOWER_IMAGE));
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerDao.findByCategory request", e);
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
                flower.setId(resultSet.getInt(FLOWER_ID));
                flower.setName(resultSet.getString(FLOWER_NAME));
                flower.setDescription(resultSet.getString(FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(FLOWER_IMAGE));
                flower.setOriginCountry(resultSet.getString(FLOWER_COUNTRY));
                flower.setLight(resultSet.getBoolean(FLOWER_LIGHT));
                flower.setWatering(resultSet.getInt(FLOWER_WATERING));
                FlowerType flowerType = new FlowerType();
                flowerType.setId(resultSet.getInt(FLOWER_TYPE_ID));
                FlowerCategory flowerCategory = FlowerCategory.valueOf(resultSet.getString(FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(flowerCategory);
                flowerType.setDescription(resultSet.getString(FLOWER_TYPE_DESCRIPTION));
                flower.setFlowerType(flowerType);
                Soil soil = Soil.valueOf(resultSet.getString(SOIL));
                flower.setSoil(soil);
                Storage storage = new Storage();
                storage.setCount(resultSet.getInt(STORAGE_COUNT));
                flower.setStorage(storage);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerDao.findById request", e);
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
            throw new DaoException("Can't handle FlowerDao.updateFlowerImage request", e);
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
                flower.setId(resultSet.getInt(FLOWER_ID));
                flower.setName(resultSet.getString(FLOWER_NAME));
                flower.setDescription(resultSet.getString(FLOWER_DESCRIPTION));
                flower.setPrice(resultSet.getDouble(FLOWER_PRICE));
                flower.setFlowerImage(resultSet.getString(FLOWER_IMAGE));
                flower.setOriginCountry(resultSet.getString(FLOWER_COUNTRY));
                flower.setLight(resultSet.getBoolean(FLOWER_LIGHT));
                flower.setWatering(resultSet.getInt(FLOWER_WATERING));
                FlowerType flowerType = new FlowerType();
                flowerType.setId(resultSet.getInt(FLOWER_TYPE_ID));
                FlowerCategory flowerCategory = FlowerCategory.valueOf(resultSet.getString(FLOWER_TYPE_CATEGORY));
                flowerType.setCategory(flowerCategory);
                flowerType.setDescription(resultSet.getString(FLOWER_TYPE_DESCRIPTION));
                flower.setFlowerType(flowerType);
                Soil soil = Soil.valueOf(resultSet.getString(SOIL));
                flower.setSoil(soil);
                Storage storage = new Storage();
                storage.setCount(resultSet.getInt(STORAGE_COUNT));
                flower.setStorage(storage);
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerDao.findAllFlowerList request", e);
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
            throw new DaoException("Can't handle FlowerDao.updateFlower request", e);
        }
    }

    @Override
    public void deleteById(int id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FLOWER_SQL)) {
            statement.setInt(FindFlowerIndex.INDEX, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't handle FlowerDao.deleteById request", e);
        }
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
