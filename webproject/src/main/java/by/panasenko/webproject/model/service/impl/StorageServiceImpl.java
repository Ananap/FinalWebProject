package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.StorageDao;
import by.panasenko.webproject.model.service.StorageService;
import by.panasenko.webproject.validator.FlowerValidator;

public class StorageServiceImpl implements StorageService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final StorageDao storageDao = daoProvider.getStorageDao();

    @Override
    public Storage findByFlowerId(String flowerId) throws ServiceException {
        Storage storage;
        try {
            storage = storageDao.findByFlowerId(Integer.parseInt(flowerId));
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findByFlowerId request at StorageService", e);
        }
        return storage;
    }

    @Override
    public void createStorage(Flower flower, String count) throws ServiceException {
        if (!FlowerValidator.validateQuantity(count)) {
            throw new ServiceException("Storage data didn't passed validation");
        }
        Storage storage = new Storage(flower, Integer.parseInt(count));
        try {
            storageDao.insertStorage(storage);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle createStorage request at StorageService", e);
        }
    }

    @Override
    public void updateStorage(String flowerId, String flowerCount) throws ServiceException {
        if (!FlowerValidator.validateQuantity(flowerCount)) {
            throw new ServiceException("Storage data didn't passed validation");
        }
        try {
            int id = Integer.parseInt(flowerId);
            int count = Integer.parseInt(flowerCount);
            storageDao.updateStorageByFlower(id, count);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateStorage request at StorageService", e);
        }
    }
}
