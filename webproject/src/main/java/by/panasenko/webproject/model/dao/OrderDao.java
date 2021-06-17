package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.Order;
import by.panasenko.webproject.exception.DaoException;

import java.util.List;

public interface OrderDao {
    Order saveOrder(Order order) throws DaoException;

    List<Order> findByUserId(Integer id) throws DaoException;

    List<Order> findAll() throws DaoException;

    Order findById(int id) throws DaoException;

    void updateStatusById(String orderStatus, Integer orderId) throws DaoException;
}
