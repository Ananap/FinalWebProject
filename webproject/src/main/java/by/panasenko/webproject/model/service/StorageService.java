package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.ServiceException;

public interface StorageService {
    Storage findByFlowerId(String flowerId) throws ServiceException;

    void createStorage(Flower flower, String count) throws ServiceException;

    void updateStorage(String flowerId, String count) throws ServiceException;
}
