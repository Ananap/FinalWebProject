package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;

public interface FlowerTypeService {
    List<FlowerType> findAll() throws ServiceException;

    FlowerType findById(String id) throws ServiceException;

    FlowerType findByCategory(String category) throws ServiceException;
}
