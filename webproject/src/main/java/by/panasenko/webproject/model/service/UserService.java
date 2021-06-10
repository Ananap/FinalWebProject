package by.panasenko.webproject.model.service;

import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;
import by.panasenko.webproject.model.dao.ResultCode;

import java.util.Optional;

public interface UserService {
    Optional<User> signIn(SignInData signInData) throws ServiceException;

    ResultCode signUp(SignUpData signUpData) throws ServiceException;

    boolean forgetPassword(String email) throws ServiceException;

    ResultCode updateUser(SignInData signInData, User user, String newPassword, String confirmPassword) throws ServiceException;
}
