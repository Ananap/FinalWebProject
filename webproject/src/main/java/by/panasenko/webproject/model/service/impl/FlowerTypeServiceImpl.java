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
            int typeId = Integer.parseInt(id);
            flowerType = flowerTypeDao.findById(typeId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findById request at FlowerTypeService", e);
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
