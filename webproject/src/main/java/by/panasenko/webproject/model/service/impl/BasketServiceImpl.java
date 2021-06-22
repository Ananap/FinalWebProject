package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.Basket;
import by.panasenko.webproject.entity.BasketFlower;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.BasketDao;
import by.panasenko.webproject.model.dao.BasketFlowerDao;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.service.BasketService;

import java.math.BigDecimal;
import java.util.List;

public class BasketServiceImpl implements BasketService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final BasketDao basketDao = daoProvider.getBasketDao();
    private static final BasketFlowerDao basketFlowerDao = daoProvider.getBasketFlowerDao();

    @Override
    public Basket findUserBasket(Integer id) throws ServiceException {
        Basket basket;
        try {
            basket = basketDao.findByUserId(id);
            if (basket.getId() == 0) {
                Basket userBasket = basketDao.createBasket(id);
                return userBasket;
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findUserBasket at BasketService", e);
        }
        return basket;
    }

    @Override
    public Basket findById(String basketId) throws ServiceException {
        Basket basket;
        try {
            basket = basketDao.findById(Integer.parseInt(basketId));
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findUserBasket at BasketService", e);
        }
        return basket;
    }

    @Override
    public void updateBasket(Basket basket, List<BasketFlower> basketFlowerList) throws ServiceException {
        BigDecimal basketTotal = new BigDecimal(0);
        for (BasketFlower basketFlower : basketFlowerList) {
            if (basketFlower.getFlower().getStorage().getCount() > 0) {
                updateBasketFlower(basketFlower);
                basketTotal = basketTotal.add(basketFlower.getSubTotal());
            }
        }
        basket.setTotalCost(basketTotal);
        try {
            basketDao.updateBasket(basket);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateBasket at BasketService", e);
        }
    }

    @Override
    public void updateTotalCost(Basket basket) throws ServiceException {
        try {
            basketDao.updateBasket(basket);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateTotalCost at BasketService", e);
        }
    }

    private void updateBasketFlower(BasketFlower basketFlower) throws ServiceException {
        BigDecimal bigDecimal = new BigDecimal(basketFlower.getFlower().getPrice()).multiply(new BigDecimal(basketFlower.getCount()));
        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        basketFlower.setSubTotal(bigDecimal);
        try {
            basketFlowerDao.updateSubTotal(basketFlower);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateBasketFlower at BasketService", e);
        }
    }
}
