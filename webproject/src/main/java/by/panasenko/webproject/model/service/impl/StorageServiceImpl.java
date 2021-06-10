package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.StorageDao;
import by.panasenko.webproject.model.service.StorageService;

public class StorageServiceImpl implements StorageService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final StorageDao storageDao = daoProvider.getStorageDao();

    @Override
    public Storage findByFlowerId(String flowerId) throws ServiceException {
        Storage storage;
        try {
            storage = storageDao.findByFlowerId(flowerId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findByFlowerId request at StorageService", e);
        }
        return storage;
    }
}
