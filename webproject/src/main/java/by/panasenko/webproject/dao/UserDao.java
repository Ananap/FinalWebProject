package by.panasenko.webproject.dao;

import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;

public interface UserDao {
    User signIn(SignInData signInData) throws DAOException;

    ResultCode signUp(SignUpData signUpData) throws DAOException;
}
