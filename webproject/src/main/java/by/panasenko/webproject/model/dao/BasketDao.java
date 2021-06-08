package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.exception.DAOException;

public interface BasketDao {
    Basket findByUserId(Integer id) throws DAOException;

    Basket createBasket(Integer id) throws DAOException;

    void updateBasket(Basket basket) throws DAOException;
}
