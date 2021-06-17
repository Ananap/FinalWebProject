package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.*;
import by.panasenko.webproject.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> createOrder(String address, String cash, String date, String time, User user, Basket basket) throws ServiceException;

    List<OrderFlower> createOrderFlowerByOrder(Order order, List<BasketFlower> basketFlowerList) throws ServiceException;

    Order saveOrder(Order order) throws ServiceException;

    List<Order> findByUser(Integer id) throws ServiceException;

    List<Order> findAll() throws ServiceException;

    Order findById(String orderId) throws ServiceException;

    List<OrderFlower> findByOrder(int id) throws ServiceException;

    void changeStatus(String orderStatus, String orderId) throws ServiceException;
}
