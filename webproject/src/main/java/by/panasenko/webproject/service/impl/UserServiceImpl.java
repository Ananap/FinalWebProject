package by.panasenko.webproject.service.impl;

import by.panasenko.webproject.dao.DaoProvider;
import by.panasenko.webproject.dao.ResultCode;
import by.panasenko.webproject.dao.UserDao;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.service.UserService;

public class UserServiceImpl implements UserService {
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final UserDao userDao = daoProvider.getUserDao();

    @Override
    public User signIn(SignInData signInData) throws ServiceException {
        try {
            return userDao.signIn(signInData);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle signIn request at UserService", e);
        }
    }

    @Override
    public ResultCode signUp(SignUpData signUpData) throws ServiceException {
        /*if (!userValidator.validate(signUpData)) {
            throw new ServiceException("User data didn't passed validation");
        } else {*/
        try {
            return userDao.signUp(signUpData);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle signUp request at UserService", e);
        }
    }
}
