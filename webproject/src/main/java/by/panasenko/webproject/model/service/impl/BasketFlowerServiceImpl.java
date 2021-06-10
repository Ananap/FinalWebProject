package by.panasenko.webproject.model.service.impl;

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
    public void addToBasket(int id, String flowerId, String count, String price) throws ServiceException {
        if (!FlowerValidator.validateFlowerId(count)) {
            throw new ServiceException("Flower data didn't passed validation");
        }
        try {
            BigDecimal subTotal = new BigDecimal(price).multiply(new BigDecimal(count));
            basketFlowerDao.addItemToBasket(id, flowerId, count, subTotal);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findById request at FlowerService", e);
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
}
