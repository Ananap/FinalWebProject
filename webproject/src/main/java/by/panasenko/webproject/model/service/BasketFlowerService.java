package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;

public interface BasketFlowerService {
    void addToBasket(int id, String flowerId, String count, String price) throws ServiceException;
    List<BasketFlower> findByBasketId(int id) throws ServiceException;
    void updateBasketFlower(String basket_flower_id, String count) throws ServiceException;
    void deleteBasketFlower(String basketFlowerId) throws ServiceException;
    Basket clearBasket(Basket basket) throws ServiceException;
}
