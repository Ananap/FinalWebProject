package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;

public interface BasketService {
    Basket findUserBasket(Integer id) throws ServiceException;
    void updateBasket(Basket basket, List<BasketFlower> basketFlowerList) throws ServiceException;
}
