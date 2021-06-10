package by.panasenko.webproject.model.service.impl;

import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.DaoProvider;
import by.panasenko.webproject.model.dao.ResultCode;
import by.panasenko.webproject.model.dao.UserDao;
import by.panasenko.webproject.model.service.UserService;
import by.panasenko.webproject.util.MailSender;
import by.panasenko.webproject.util.PasswordEncryptor;
import by.panasenko.webproject.validator.UserValidator;

public class UserServiceImpl implements UserService {
    private static final UserValidator userValidator = UserValidator.getInstance();
    private static final DaoProvider daoProvider = DaoProvider.getInstance();
    private static final UserDao userDao = daoProvider.getUserDao();

    @Override
    public boolean forgetPassword(String email) throws ServiceException {
        if (!userValidator.validateEmail(email)) {
            throw new ServiceException("User email didn't passed validation");
        } else {
            try {
                User user = userDao.findUserByEmail(email);
                if (user != null) {
                    String newPassword = PasswordEncryptor.generateRandomPassword();
                    userDao.setPasswordById(user.getId(), newPassword);
                    MailSender.sendEmail(email, MailSender.messageForgetPassword(user.getUsername(), newPassword));
                    return true;
                }
            } catch (DaoException e) {
                throw new ServiceException("Can't handle forgetPassword request at UserService", e);
            }
        }
        return false;
    }

    @Override
    public ResultCode signUp(SignUpData signUpData) throws ServiceException {
        if (!userValidator.validate(signUpData)) {
            throw new ServiceException("User data didn't passed validation");
        } else {
            try {
                return userDao.signUp(signUpData);
            } catch (DaoException e) {
                throw new ServiceException("Can't handle signUp request at UserService", e);
            }
        }
    }

    @Override
    public User signIn(SignInData signInData) throws ServiceException {
        try {
            return userDao.signIn(signInData);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle signIn request at UserService", e);
        }
    }

    @Override
    public ResultCode updateUser(SignInData signInData, User user, String newPassword, String confirmPassword) throws ServiceException {
        if (signIn(signInData) == null) {
            return ResultCode.WRONG_PASSWORD;
        }
        if (!userValidator.validate(user)) {
            throw new ServiceException("User data didn't passed validation");
        }
        try {
            User userForUpdate;
            if (!newPassword.equals(confirmPassword)) {
                return ResultCode.WRONG_CONFIRMATION;
            } else {
                userForUpdate = createUser(user, newPassword, confirmPassword);
            }
            return userDao.updateUser(userForUpdate);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateUser request at UserService", e);
        }
    }

    private User createUser(User user, String newPassword, String confirmPassword) {
        if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
        return user;
    }
}
