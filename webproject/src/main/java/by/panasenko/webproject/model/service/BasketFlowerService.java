package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;

public interface BasketFlowerService {
    void addToBasket(int id, String flowerId, String count, String price) throws ServiceException;

    List<BasketFlower> findByBasketId(int id) throws ServiceException;
}
