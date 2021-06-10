package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.FlowerDao;
import by.panasenko.webproject.model.service.FlowerService;
import by.panasenko.webproject.validator.FlowerValidator;

import java.util.List;

public class FlowerServiceImpl implements FlowerService {
    private static final FlowerValidator flowerValidator = FlowerValidator.getInstance();
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
        if (!flowerValidator.validateFlowerTypeId(category)) {
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
        if (!flowerValidator.validateFlowerId(flowerId)) {
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
}
