package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.OrderFlower;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

public interface OrderFlowerDao {
    void saveOrderFlower(OrderFlower orderFlower) throws DaoException;

    List<OrderFlower> findByOrder(int id) throws DaoException;
}
