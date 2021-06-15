package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.*;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.OrderDao;
import by.panasenko.webproject.model.dao.OrderFlowerDao;
import by.panasenko.webproject.model.dao.StorageDao;
import by.panasenko.webproject.model.service.OrderService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final OrderDao orderDao = daoProvider.getOrderDao();
    private static final OrderFlowerDao orderFlowerDao = daoProvider.getOrderFlowerDao();
    private static final StorageDao storageDao = daoProvider.getStorageDao();

    @Override
    public Order saveOrder(Order order) throws ServiceException {
        Order savedOrder;
        try {
            savedOrder = orderDao.saveOrder(order);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle saveOrder request at OrderService", e);
        }
        return savedOrder;
    }

    @Override
    public Optional<Order> createOrder(String address, String cash, String date, String time, User user, Basket basket) {
        if (address.isEmpty() || cash.isEmpty() || date.isEmpty() || time.isEmpty()) {
            return Optional.empty();
        }
        boolean orderCash = Boolean.parseBoolean(cash);
        Date orderDate = parseOrderDate(date);
        BigDecimal orderTotalCost = basket.getTotalCost();
        Order order = new Order(Status.INPROCESS, address, orderDate, Calendar.getInstance().getTime(), time, orderTotalCost, user, orderCash);
        return Optional.of(order);
    }

    @Override
    public List<OrderFlower> createOrderFlowerByOrder(Order order, List<BasketFlower> basketFlowerList) throws ServiceException {
        List<OrderFlower> orderFlowerList = new ArrayList<>();
        for (BasketFlower basketFlower : basketFlowerList) {
            Flower flower = basketFlower.getFlower();
            OrderFlower orderFlower = new OrderFlower();
            orderFlower.setOrder(order);
            orderFlower.setFlower(flower);
            orderFlower.setCount(basketFlower.getCount());
            orderFlower.setSubTotal(basketFlower.getSubTotal());
            orderFlowerList.add(orderFlower);
            Storage storage = flower.getStorage();
            int count = storage.getCount() - basketFlower.getCount();
            updateStorage(flower.getId(), count);
            try {
                orderFlowerDao.saveOrderFlower(orderFlower);
            } catch (DaoException e) {
                throw new ServiceException("Can't handle createOrderFlowerByOrder request at OrderService", e);
            }
        }
        return orderFlowerList;
    }

    private void updateStorage(Integer flowerId, Integer count) throws ServiceException {
        try {
            Storage dbStorage = storageDao.findByFlowerId(flowerId);
            dbStorage.setCount(count);
            storageDao.updateStorage(dbStorage);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle saveOrder request at OrderService", e);
        }
    }

    private Date parseOrderDate(String date) {
        SimpleDateFormat formatTemplate = new SimpleDateFormat("yyyy-MM-dd");
        Date parsingDate = null;
        try {
            parsingDate = formatTemplate.parse(date);
        } catch (ParseException e) {
            logger.error("Can't parse order date", e);
        }
        return parsingDate;
    }
}
