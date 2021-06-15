package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.exception.DaoException;

public interface OrderDao {
    Order saveOrder(Order order) throws DaoException;
}
