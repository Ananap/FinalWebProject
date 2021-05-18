package by.panasenko.webproject.dao.impl;

import by.panasenko.webproject.dao.ResultCode;
import by.panasenko.webproject.dao.UserDao;
import by.panasenko.webproject.dao.connection.ConnectionPool;
import by.panasenko.webproject.entity.Role;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static final UserDaoImpl instance = new UserDaoImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT id, username, password, email, role, role.name, first_name, last_name, address FROM Users users " +
            "JOIN Role role ON users.role = role.id " +
            "WHERE (username = ?)";
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDAO.signIn request";

    public static UserDaoImpl getInstance() {
        return instance;
    }

    private UserDaoImpl() {
    }

    @Override
    public User signIn(SignInData signInData) throws DAOException {
        User user = null;

        try (Connection connection = connectionPool.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL);

            statement.setString(SignUpIndex.USERNAME, signInData.getUsername());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(ParamColumn.PASSWORD);

                if (!passwordEncryptor.checkHash(signInData.getPassword(), hashedPassword)) {
                    return null;
                }

                user = new User();
                Role role = new Role();

                role.setId(rs.getInt(ParamColumn.ROLE_ID));
                role.setName(rs.getString(ParamColumn.ROLE_NAME));

                user.setId(rs.getInt(ParamColumn.ID));
                user.setUsername(rs.getString(ParamColumn.USERNAME));
                user.setPassword(rs.getString(ParamColumn.PASSWORD));
                user.setEmail(rs.getString(ParamColumn.EMAIL));
                user.setRole(role);
                user.setFirstName(rs.getString(ParamColumn.FIRST_NAME));
                user.setLastName(rs.getString(ParamColumn.LAST_NAME));
                user.setAddress(rs.getString(ParamColumn.ADDRESS));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SIGN_IN_PROBLEM, e);
        }
        return user;
    }

    @Override
    public ResultCode signUp(SignUpData signUpData) throws DAOException {
        return ResultCode.RESULT_SUCCESS;
    }

    private static class SignUpIndex {
        private static final int USERNAME = 1;
        private static final int PASSWORD = 2;
        private static final int EMAIL = 3;
        private static final int ROLE = 4;
        private static final int FIRST_NAME = 5;
        private static final int LAST_NAME = 6;
        private static final int ADDRESS = 7;
    }

    private static class ParamColumn {
        private static final String ID = "id";
        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";
        private static final String EMAIL = "email";
        private static final String ROLE_ID = "role";
        private static final String ROLE_NAME = "role.name";
        private static final String FIRST_NAME = "first_name";
        private static final String LAST_NAME = "last_name";
        private static final String ADDRESS = "address";
    }
}
