package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface BasketFlowerDao {
    void addItemToBasket(int id, String flowerId, String count, BigDecimal subTotal) throws DaoException;

    List<BasketFlower> findByBasketId(int id) throws DaoException;

    void updateBasketFlower(BasketFlower basketFlower) throws DaoException;

    void setCountBasketFlower(BasketFlower basketFlower) throws DaoException;

    BasketFlower findById(int id) throws DaoException;

    void deleteBasketFlower(int id) throws DaoException;
}
