package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.OrderFlower;
import by.panasenko.webproject.exception.DaoException;

public interface OrderFlowerDao {
    void saveOrderFlower(OrderFlower orderFlower) throws DaoException;
}
