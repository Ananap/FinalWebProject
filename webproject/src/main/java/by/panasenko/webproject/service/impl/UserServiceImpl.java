package by.panasenko.webproject.service.impl;

import by.panasenko.webproject.dao.UserDAOImpl;
import by.panasenko.webproject.dao.UserDao;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private UserDao userDao = UserDAOImpl.getInstance();

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public User findUserByName(String name) throws ServiceException {
        try {
            return userDao.findUserByName(name);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle findUserByName request at UserService", e);
        }
    }

    @Override
    public List<User> findUserList() throws ServiceException {
        try {
            return userDao.findUserList();
        } catch (DAOException e) {
            throw new ServiceException("Can't handle findUserList request at UserService",e);
        }
    }
}
