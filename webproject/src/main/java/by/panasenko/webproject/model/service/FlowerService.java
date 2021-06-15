package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.Flower;
import by.panasenko.webproject.entity.FlowerType;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;

public interface FlowerService {
    List<Flower> findAll() throws ServiceException;

    List<Flower> findByCategory(String category) throws ServiceException;

    Flower findById(String flowerId) throws ServiceException;

    List<Flower> findAllFlowerList() throws ServiceException;

    Flower createFlower(String nameFlower, String description, String price,
                        String soil, String watering, String light,
                        String country, FlowerType flowerType) throws ServiceException;

    void updateFlower(String flowerId, String nameFlower, String description,
                      String price, String soil, String watering, String light,
                      String country, FlowerType flowerType) throws ServiceException;

    void deleteFlowerById(String flowerId) throws ServiceException;
}
