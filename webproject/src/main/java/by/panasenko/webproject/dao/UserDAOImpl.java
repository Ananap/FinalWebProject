package by.panasenko.webproject.dao;

import by.panasenko.webproject.dao.connection.ConnectionPool;
import by.panasenko.webproject.dao.connection.impl.ConnectionPoolImpl;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDao {
    private static final UserDAOImpl instance = new UserDAOImpl();
    private static final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private static final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();
    private static final String FIND_USER_BY_NAME = "SELECT userId, name, age FROM users" + "WHERE (users.name = ?)";
    private static final String FIND_ALL_USERS = "SELECT userId, name, age FROM users";
    private static final String GET_USER_BY_LOGIN_SQL = "SELECT users FROM Users users " + "WHERE (login = ?)";
    private static final String MESSAGE_FIND_ALL_USERS_PROBLEM = "Can't handle UserDAO.findUserList request";
    private static final String MESSAGE_FIND_USER_BY_NAME_PROBLEM = "Can't handle UserDAO.findUserByName request";
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDAO.signIn request";

    public static UserDAOImpl getInstance() {
        return instance;
    }

    private UserDAOImpl() { }

    @Override
    public User signIn(String login, String password) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(ParamColumn.PASSWORD);

                if (!passwordEncryptor.checkHash(password, hashedPassword)) {
                    return null;
                }
                user = new User();
                user.setUserId(rs.getLong(ParamColumn.ID));
                user.setName(rs.getString(ParamColumn.NAME));
                user.setAge(rs.getInt(ParamColumn.AGE));
                user.setLogin(rs.getString(ParamColumn.LOGIN));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SIGN_IN_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    @Override
    public List<User> findUserList() throws DAOException {
        final List<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
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
            connectionPool.closeConnection(connection, ps);
        }
        return userList;
    }

    @Override
    public User findUserByName(String name) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
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
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    public static class ParamColumn {
        private static final String ID = "userId";
        private static final String NAME = "name";
        private static final String AGE = "age";
        private static final String LOGIN = "login";
        private static final String PASSWORD = "password";
    }
}
