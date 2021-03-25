package by.panasenko.webproject.service;

import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User findUserByName(String name) throws ServiceException, SQLException;
    List<User> findUserList() throws ServiceException, SQLException;
}
