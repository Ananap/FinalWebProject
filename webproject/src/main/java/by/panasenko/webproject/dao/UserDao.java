package by.panasenko.webproject.dao;

import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;

import java.util.List;

public interface UserDao {
    List<User> findUserList() throws DAOException;
    User findUserByName(String name) throws DAOException;
}
