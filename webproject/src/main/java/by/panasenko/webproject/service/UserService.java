package by.panasenko.webproject.service;

import by.panasenko.webproject.dao.ResultCode;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;

public interface UserService {
    User signIn(SignInData signInData) throws ServiceException;
    ResultCode signUp(SignUpData signUpData) throws ServiceException;

    String generateRandomPassword();
}
