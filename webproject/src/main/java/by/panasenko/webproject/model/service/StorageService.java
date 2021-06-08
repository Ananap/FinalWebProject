package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.Storage;
import by.panasenko.webproject.exception.ServiceException;

public interface StorageService {
    Storage findByFlowerId(String flowerId) throws ServiceException;
}
