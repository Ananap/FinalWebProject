package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.BasketFlowerDao;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.service.BasketFlowerService;
import by.panasenko.webproject.validator.FlowerValidator;

import java.math.BigDecimal;
import java.util.List;

public class BasketFlowerServiceImpl implements BasketFlowerService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final BasketFlowerDao basketFlowerDao = daoProvider.getBasketFlowerDao();

    @Override
    public void addToBasket(int basketId, String flowerId, String count, String price) throws ServiceException {
        if (!FlowerValidator.validateId(flowerId) || !FlowerValidator.validateQuantity(count)) {
            throw new ServiceException("Flower data didn't passed validation");
        }
        try {
            BigDecimal subTotal = new BigDecimal(price).multiply(new BigDecimal(count));
            int flowerCount = Integer.parseInt(count);
            int id = Integer.parseInt(flowerId);
            basketFlowerDao.addItemToBasket(basketId, id, flowerCount, subTotal);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addToBasket request at BasketFlowerService", e);
        }
    }

    @Override
    public List<BasketFlower> findByBasketId(int id) throws ServiceException {
        List<BasketFlower> basketFlowerList;
        try {
            basketFlowerList = basketFlowerDao.findByBasketId(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findByBasketId request at BasketFlowerService", e);
        }
        return basketFlowerList;
    }

    @Override
    public void updateBasketFlower(String basketFlowerId, String count) throws ServiceException {
        if (!FlowerValidator.validateId(count)) {
            throw new ServiceException("Data didn't passed validation");
        }
        try {
            BasketFlower basketFlower = basketFlowerDao.findById(Integer.parseInt(basketFlowerId));
            basketFlower.setCount(Integer.parseInt(count));
            basketFlowerDao.updateCount(basketFlower);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateBasketFlower request at BasketFlowerService", e);
        }
    }

    @Override
    public void deleteBasketFlower(String basketFlowerId) throws ServiceException {
        if (!FlowerValidator.validateId(basketFlowerId)) {
            throw new ServiceException("Data didn't passed validation");
        }
        try {
            basketFlowerDao.deleteBasketFlower(Integer.parseInt(basketFlowerId));
        } catch (DaoException e) {
            throw new ServiceException("Can't handle deleteBasketFlower request at BasketFlowerService", e);
        }
    }

    @Override
    public Basket clearBasket(Basket basket) throws ServiceException {
        List<BasketFlower> basketFlowerList = findByBasketId(basket.getId());
        for (BasketFlower basketFlower : basketFlowerList) {
            deleteBasketFlower(String.valueOf(basketFlower.getId()));
        }
        basket.setTotalCost(new BigDecimal(0));
        return basket;
    }
}
