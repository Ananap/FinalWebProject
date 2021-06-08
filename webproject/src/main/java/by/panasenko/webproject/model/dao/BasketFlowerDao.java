package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface BasketFlowerDao {
    void addItemToBasket(int id, String flowerId, String count, BigDecimal subTotal) throws DAOException;

    List<BasketFlower> findByBasketId(int id) throws DAOException;

    void updateBasketFlower(BasketFlower basketFlower) throws DAOException;
}
