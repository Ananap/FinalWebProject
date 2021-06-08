package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;

public interface FlowerService {
    List<Flower> findAll() throws ServiceException;

    List<Flower> findByCategory(String category) throws ServiceException;

    Flower findById(String flowerId) throws ServiceException;
}
