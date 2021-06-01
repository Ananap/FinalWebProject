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

/**
 * Implementation of {@link UserDao}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class UserDaoImpl implements UserDao {

    /** A single instance of the class (pattern Singleton) */
    private static final UserDaoImpl instance = new UserDaoImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** An object of {@link PasswordEncryptor} */
    private static final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();

    /** Query for database to get user by username */
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT id, username, password, email, role, role.name, first_name, last_name, address, phone FROM Users users " +
            "JOIN Role role ON users.role = role.id " +
            "WHERE (username = ?)";

    /** Query for database to sign up new user */
    private static final String SIGNUP_SQL = "INSERT INTO users (username, password, email, role_id, first_name, last_name, address, phone) VALUES (?,?,?,?,?,?,?,?)";

    /** Message, that is putted in Exception if there are sign ip problem */
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDao.signIn request";

    /** Message, that is putted in Exception if there are sign up problem */
    private static final String MESSAGE_SIGN_UP_PROBLEM = "Can't handle UserDao.signUp request";

    /**
     * Returns the instance of the class
     * @return Object of {@link UserDaoImpl}
     */
    public static UserDaoImpl getInstance() {
        return instance;
    }

    /** Private constructor without parameters */
    private UserDaoImpl() {
    }


    /**
     * Connects to database, checks the credentials and returns an User object if success.
     *
     * @param signInData    is Object of {@link SignInData}, which contains information about user's username and password.
     * @return {@link User} if user's data exists and password matches, null if user's username and password are not correct.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public User signIn(SignInData signInData) throws DAOException {
        User user = null;
        try (Connection connection = connectionPool.getConnection()) {
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

    /**
     * Connects to database, creates new user by data provided and returns {@link ResultCode} object as result.
     *
     * @param signUpData Object of {@link SignUpData}, which contains user information.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public ResultCode signUp(SignUpData signUpData) throws DAOException {
        final int STATUS_USER = 1;
        final int DUBLICATE_EMAIL_ERROR_CODE = 1062;

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SIGNUP_SQL);
            ps.setString(SignUpIndex.USERNAME, signUpData.getUsername());
            ps.setString(SignUpIndex.EMAIL, signUpData.getEmail());
            ps.setString(SignUpIndex.PASSWORD, passwordEncryptor.getHash(signUpData.getPassword()));
            ps.setInt(SignUpIndex.ROLE, STATUS_USER);
            ps.setString(SignUpIndex.FIRST_NAME, signUpData.getFirstName());
            ps.setString(SignUpIndex.LAST_NAME, signUpData.getLastName());
            ps.setString(SignUpIndex.ADDRESS, signUpData.getAddress());
            ps.setString(SignUpIndex.PHONE, signUpData.getPhoneNumber());
            ps.execute();
            return ResultCode.RESULT_SUCCESS;

        } catch (SQLException e) {
            if (e.getErrorCode() == DUBLICATE_EMAIL_ERROR_CODE) {
                return ResultCode.RESULT_ERROR_DUPLICATE_EMAIL;
            } else {
                throw new DAOException(MESSAGE_SIGN_UP_PROBLEM, e);
            }
        }
    }

    private static class SignUpIndex {
        private static final int USERNAME = 1;
        private static final int PASSWORD = 2;
        private static final int EMAIL = 3;
        private static final int ROLE = 4;
        private static final int FIRST_NAME = 5;
        private static final int LAST_NAME = 6;
        private static final int ADDRESS = 7;
        private static final int PHONE = 8;
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
        private static final String PHONE = "phone";
    }
}
