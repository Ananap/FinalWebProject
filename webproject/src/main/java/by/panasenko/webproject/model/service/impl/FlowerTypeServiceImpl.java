package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.FlowerTypeDao;
import by.panasenko.webproject.model.service.FlowerTypeService;
import by.panasenko.webproject.validator.FlowerValidator;

import java.util.List;

public class FlowerTypeServiceImpl implements FlowerTypeService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final FlowerTypeDao flowerTypeDao = daoProvider.getFlowerTypeDao();

    @Override
    public FlowerType findById(String id) throws ServiceException {
        if (!FlowerValidator.validateFlowerTypeId(id)) {
            throw new ServiceException("Flower type data didn't passed validation");
        }
        FlowerType flowerType;
        try {
            flowerType = flowerTypeDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findById request at FlowerTypeService", e);
        }
        return flowerType;
    }

    @Override
    public FlowerType findByCategory(String category) throws ServiceException {
        if (!FlowerValidator.validateCategory(category)) {
            throw new ServiceException("Flower type data didn't passed validation");
        }
        FlowerType flowerType;
        try {
            flowerType = flowerTypeDao.findByCategory(category);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findByCategory request at FlowerTypeService", e);
        }
        return flowerType;
    }

    @Override
    public List<FlowerType> findAll() throws ServiceException {
        List<FlowerType> flowerTypeList;
        try {
            flowerTypeList = flowerTypeDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAll request at FlowerTypeService", e);
        }
        return flowerTypeList;
    }
}
