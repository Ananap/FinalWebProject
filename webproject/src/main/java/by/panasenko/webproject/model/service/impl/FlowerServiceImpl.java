package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.entity.Soil;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.FlowerDao;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.validator.FlowerValidator;

import java.util.List;

public class FlowerServiceImpl implements FlowerService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final FlowerDao flowerDao = daoProvider.getFlowerDao();

    @Override
    public List<Flower> findAll() throws ServiceException {
        List<Flower> flowerList;
        try {
            flowerList = flowerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAll request at FlowerService", e);
        }
        return flowerList;
    }

    @Override
    public List<Flower> findByCategory(String category) throws ServiceException {
        if (!FlowerValidator.validateFlowerTypeId(category)) {
            throw new ServiceException("Flower Type data didn't passed validation");
        }
        List<Flower> flowerList;
        try {
            flowerList = flowerDao.findByCategory(category);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findByCategory request at FlowerService", e);
        }
        return flowerList;
    }

    @Override
    public Flower findById(String flowerId) throws ServiceException {
        if (!FlowerValidator.validateId(flowerId)) {
            throw new ServiceException("Flower data didn't passed validation");
        }
        Flower flower;
        try {
            flower = flowerDao.findById(flowerId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findById request at FlowerService", e);
        }
        return flower;
    }

    @Override
    public Flower createFlower(String nameFlower, String description, String price, String soil, String watering, String light, String country, FlowerType category) throws ServiceException {
        final String FORMAT_FILE_NAME = ".png";
        if (!FlowerValidator.validateData(nameFlower, description, price, soil, watering, light)) {
            throw new ServiceException("Flower data didn't passed validation");
        }
        double flowerPrice = Double.parseDouble(price);
        int flowerWatering = Integer.parseInt(watering);
        boolean flowerLight = Boolean.parseBoolean(light);
        Soil flowerSoil = Soil.valueOf(soil);
        Flower flower = new Flower(nameFlower, description, flowerPrice, flowerSoil, flowerWatering, flowerLight, country, category);
        Flower dbFlower;
        try {
            dbFlower = flowerDao.createFlower(flower);
            String flowerImage = flower.getId() + FORMAT_FILE_NAME;
            dbFlower.setFlowerImage(flowerImage);
            flowerDao.updateFlowerImage(dbFlower);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle createFlower request at FlowerService", e);
        }
        return dbFlower;
    }

    @Override
    public void updateFlower(String flowerId, String nameFlower, String description, String price, String soil, String watering, String light, String country, FlowerType flowerType) throws ServiceException {
        if (!FlowerValidator.validateData(nameFlower, description, price, soil, watering, light) || !FlowerValidator.validateId(flowerId)) {
            throw new ServiceException("Flower data didn't passed validation");
        }
        int id = Integer.parseInt(flowerId);
        double flowerPrice = Double.parseDouble(price);
        int flowerWatering = Integer.parseInt(watering);
        boolean flowerLight = Boolean.parseBoolean(light);
        Soil flowerSoil = Soil.valueOf(soil);
        Flower flower = new Flower(nameFlower, description, flowerPrice, flowerSoil, flowerWatering, flowerLight, country, flowerType);
        try {
            flowerDao.updateFlower(id, flower);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateFlower request at FlowerService", e);
        }
    }

    @Override
    public void deleteFlowerById(String flowerId) throws ServiceException {
        if (!FlowerValidator.validateId(flowerId)) {
            throw new ServiceException("Flower data didn't passed validation");
        }
        int id = Integer.parseInt(flowerId);
        try {
            flowerDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle deleteFlowerById request at FlowerService", e);
        }
    }

    @Override
    public List<Flower> findAllFlowerList() throws ServiceException {
        List<Flower> flowerList;
        try {
            flowerList = flowerDao.findAllFlowerList();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAllFlowerList request at FlowerService", e);
        }
        return flowerList;
    }
}
