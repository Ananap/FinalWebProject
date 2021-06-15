package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Role;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DaoException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.ColumnName;
import by.panasenko.webproject.model.dao.ResultCode;
import by.panasenko.webproject.model.dao.UserDao;
import by.panasenko.webproject.util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Implementation of {@link UserDao}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 */
public class UserDaoImpl implements UserDao {

    /** A single instance of the class (pattern Singleton) */
    private static final UserDaoImpl instance = new UserDaoImpl();

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to sign up new user */
    private static final String SIGNUP_SQL = "INSERT INTO users (username, password, email, user_role, first_name, last_name, address, phone) VALUES (?,?,?,?,?,?,?,?)";

    /** Query for database to get user by email */
    private static final String GET_USER_BY_EMAIL_SQL = "SELECT user_id, email, username, password, user_role, first_name, last_name, address, phone FROM Users users WHERE (email = ?)";

    /** Query for database to set password by user ID */
    private static final String SET_PASSWORD_BY_ID_SQL = "UPDATE Users SET password = ? WHERE user_id = ?";

    /** Query for database to update user data by user ID */
    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE Users SET username = ?, password = ?, first_name = ?, last_name = ?, address = ?, phone = ? WHERE user_id = ?";

    /** Message, that is putted in Exception if there are sign ip problem */
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDao.signIn request";

    /** Message, that is putted in Exception if there are sign up problem */
    private static final String MESSAGE_SIGN_UP_PROBLEM = "Can't handle UserDao.signUp request";

    /** Message, that is putted in Exception if there are email exists problem */
    private static final String MESSAGE_IS_EMAIL_EXISTS_PROBLEM = "Can't handle UserDao.findUserByEmail request";

    /** Message, that is putted in Exception if there are set password problem */
    private static final String MESSAGE_SET_PASSWORD_PROBLEM = "Can't handle UserDao.setPasswordByID request";

    /** Message, that is putted in Exception if there are update user problem */
    private static final String MESSAGE_UPDATE_USER_PROBLEM = "Can't handle UserDao.updateUser request";

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
     * @param signInData is Object of {@link SignInData}, which contains information about user's username and password.
     * @return {@link User} if user's data exists and password matches, null if user's username and password are not correct.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public Optional<User> signIn(SignInData signInData) throws DaoException {
        final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();
        Optional<User> optionalUser;
        User user = new User();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {
            statement.setString(FindUserIndex.EMAIL, signInData.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString(ColumnName.USERS_PASSWORD);
                if (!passwordEncryptor.checkHash(signInData.getPassword(), hashedPassword)) {
                    return Optional.empty();
                }
                user.setId(resultSet.getInt(ColumnName.USERS_ID));
                user.setUsername(resultSet.getString(ColumnName.USERS_USERNAME));
                user.setPassword(resultSet.getString(ColumnName.USERS_PASSWORD));
                user.setEmail(resultSet.getString(ColumnName.USERS_EMAIL));
                user.setRole(Role.valueOf(resultSet.getString(ColumnName.USERS_ROLE)));
                user.setFirstName(resultSet.getString(ColumnName.USERS_FIRST_NAME));
                user.setLastName(resultSet.getString(ColumnName.USERS_LAST_NAME));
                user.setAddress(resultSet.getString(ColumnName.USERS_ADDRESS));
                user.setPhone(resultSet.getString(ColumnName.USERS_PHONE));
            }
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SIGN_IN_PROBLEM, e);
        }
        return optionalUser;
    }

    /**
     * Connects to database, creates new user by data provided and returns {@link ResultCode} object as result.
     * @param signUpData Object of {@link SignUpData}, which contains user information.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DaoException when problems with database connection occurs.
     */
    @Override
    public ResultCode signUp(SignUpData signUpData) throws DaoException {
        final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();
        final String ROLE_USER = String.valueOf(Role.USER);
        final int DUBLICATE_EMAIL_ERROR_CODE = 1062;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SIGNUP_SQL)) {
            statement.setString(SignUpIndex.USERNAME, signUpData.getUsername());
            statement.setString(SignUpIndex.EMAIL, signUpData.getEmail());
            statement.setString(SignUpIndex.PASSWORD, passwordEncryptor.getHash(signUpData.getPassword()));
            statement.setString(SignUpIndex.ROLE, ROLE_USER);
            statement.setString(SignUpIndex.FIRST_NAME, signUpData.getFirstName());
            statement.setString(SignUpIndex.LAST_NAME, signUpData.getLastName());
            statement.setString(SignUpIndex.ADDRESS, signUpData.getAddress());
            statement.setString(SignUpIndex.PHONE, signUpData.getPhoneNumber());
            statement.execute();
            return ResultCode.SUCCESS;

        } catch (SQLException e) {
            if (e.getErrorCode() == DUBLICATE_EMAIL_ERROR_CODE) {
                return ResultCode.ERROR_DUPLICATE_EMAIL;
            } else {
                throw new DaoException(MESSAGE_SIGN_UP_PROBLEM, e);
            }
        }
    }

    @Override
    public User findUserByEmail(String email) throws DaoException {
        User user = new User();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {
            statement.setString(FindUserIndex.EMAIL, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(ColumnName.USERS_ID));
                user.setUsername(resultSet.getString(ColumnName.USERS_USERNAME));
                user.setPassword(resultSet.getString(ColumnName.USERS_PASSWORD));
            }
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_IS_EMAIL_EXISTS_PROBLEM, e);
        }
        return user;
    }

    @Override
    public void setPasswordById(Integer id, String password) throws DaoException {
        final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_PASSWORD_BY_ID_SQL)) {
            statement.setString(SetPasswordIndex.PASSWORD, passwordEncryptor.getHash(password));
            statement.setInt(SetPasswordIndex.ID, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_SET_PASSWORD_PROBLEM, e);
        }
    }

    @Override
    public ResultCode updateUser(User user) throws DaoException {
        final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID_SQL)) {
            statement.setString(UpdateIndex.USERNAME, user.getUsername());
            statement.setString(UpdateIndex.PASSWORD, passwordEncryptor.getHash(user.getPassword()));
            statement.setString(UpdateIndex.FIRST_NAME, user.getFirstName());
            statement.setString(UpdateIndex.LAST_NAME, user.getLastName());
            statement.setString(UpdateIndex.ADDRESS, user.getAddress());
            statement.setString(UpdateIndex.PHONE, user.getPhone());
            statement.setInt(UpdateIndex.ID, user.getId());
            statement.execute();
            return ResultCode.SUCCESS;
        } catch (SQLException e) {
            throw new DaoException(MESSAGE_UPDATE_USER_PROBLEM, e);
        }
    }

    /**
     * Static class that contains parameter indexes for sign up
     */
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

    /**
     * Static class that contains parameter indexes for setting password by user ID
     */
    private static class SetPasswordIndex {
        private static final int PASSWORD = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameter indexes for updating user data by user ID
     */
    private static class UpdateIndex {
        private static final int USERNAME = 1;
        private static final int PASSWORD = 2;
        private static final int FIRST_NAME = 3;
        private static final int LAST_NAME = 4;
        private static final int ADDRESS = 5;
        private static final int PHONE = 6;
        private static final int ID = 7;
    }

    /**
     * Static class that contains parameter indexes for getting user data by user ID
     */
    private static class FindUserIndex {
        private static final int EMAIL = 1;
    }
}
