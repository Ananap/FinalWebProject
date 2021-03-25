package by.panasenko.webproject.dao;

import by.panasenko.webproject.dao.connection.ConnectionPool;
import by.panasenko.webproject.dao.connection.impl.ConnectionPoolImpl;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDao{
    private static final UserDAOImpl instance = new UserDAOImpl();
    private static final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private static final String FIND_USER_BY_NAME = "SELECT userId, name, age FROM users" + "WHERE (users.name = ?)";
    private static final String FIND_ALL_USERS = "SELECT userId, name, age FROM users";
    private static final String MESSAGE_FIND_ALL_USERS_PROBLEM = "Cant handle UserDAO.findUserList request";
    private static final String MESSAGE_FIND_USER_BY_NAME_PROBLEM = "Cant handle UserDAO.findUserByName request";

    public static UserDAOImpl getInstance() {
        return instance;
    }

    private UserDAOImpl() { }

    @Override
    public List<User> findUserList() throws DAOException {
        final List<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(FIND_ALL_USERS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getInt(1);
                String userName = rs.getString(2);
                int age = rs.getInt(3);
                userList.add(new User(id, userName, age));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_USERS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection);
        }
        return userList;
    }

    @Override
    public User findUserByName(String name) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(FIND_USER_BY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getInt(1);
                String userName = rs.getString(2);
                int age = rs.getInt(3);
                user = new User(id, userName, age);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_USER_BY_NAME_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection);
        }
        return user;
    }
}
