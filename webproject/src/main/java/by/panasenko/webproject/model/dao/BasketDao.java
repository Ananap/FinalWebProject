package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.exception.DaoException;

public interface BasketDao {
    Basket findByUserId(Integer id) throws DaoException;

    Basket createBasket(Integer id) throws DaoException;

    void updateBasket(Basket basket) throws DaoException;
}
