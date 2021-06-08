package by.panasenko.webproject.model.dao.impl;

import by.panasenko.webproject.entity.Role;
import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DAOException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import by.panasenko.webproject.model.dao.ColumnName;
import by.panasenko.webproject.model.dao.ResultCode;
import by.panasenko.webproject.model.dao.UserDao;
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

    /**
     * An object of {@link ConnectionPool}
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * An object of {@link PasswordEncryptor}
     */
    private static final PasswordEncryptor passwordEncryptor = PasswordEncryptor.getInstance();

    /**
     * Query for database to sign up new user
     */
    private static final String SIGNUP_SQL = "INSERT INTO users (username, password, email, role_id, first_name, last_name, address, phone) VALUES (?,?,?,?,?,?,?,?)";

    /**
     * Query for database to get user by email
     */
    private static final String GET_USER_BY_EMAIL_SQL = "SELECT user_id, email, username, password, role.id, role.name, first_name, last_name, address, phone FROM Users users " +
            "JOIN Role role ON users.role_id = role.id " +
            "WHERE (email = ?)";

    /**
     * Query for database to set password src by user ID
     */
    private static final String SET_PASSWORD_BY_ID_SQL = "UPDATE Users SET password = ? WHERE user_id = ?";

    /**
     * Query for database to update user data by user ID
     */
    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE Users SET username = ?, password = ?, first_name = ?, last_name = ?, address = ?, phone = ? WHERE user_id = ?";

    /**
     * Message, that is putted in Exception if there are sign ip problem
     */
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDao.signIn request";

    /**
     * Message, that is putted in Exception if there are sign up problem
     */
    private static final String MESSAGE_SIGN_UP_PROBLEM = "Can't handle UserDao.signUp request";

    /**
     * Message, that is putted in Exception if there are email exists problem
     */
    private static final String MESSAGE_IS_EMAIL_EXISTS_PROBLEM = "Can't handle UserDao.findUserByEmail request";

    /**
     * Message, that is putted in Exception if there are set password problem
     */
    private static final String MESSAGE_SET_PASSWORD_PROBLEM = "Can't handle UserDao.setPasswordByID request";

    /**
     * Message, that is putted in Exception if there are update user problem
     */
    private static final String MESSAGE_UPDATE_USER_PROBLEM = "Can't handle UserDao.updateUser request";

    /**
     * Returns the instance of the class
     *
     * @return Object of {@link UserDaoImpl}
     */
    public static UserDaoImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
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
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {
            statement.setString(FindUserIndex.EMAIL, signInData.getEmail());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString(ColumnName.USERS_PASSWORD);
                if (!passwordEncryptor.checkHash(signInData.getPassword(), hashedPassword)) {
                    return null;
                }

                user = new User();
                Role role = new Role();

                role.setId(rs.getInt(ColumnName.USERS_ROLE));
                role.setName(rs.getString(ColumnName.USERS_ROLE_NAME));

                user.setId(rs.getInt(ColumnName.USERS_ID));
                user.setUsername(rs.getString(ColumnName.USERS_USERNAME));
                user.setPassword(rs.getString(ColumnName.USERS_PASSWORD));
                user.setEmail(rs.getString(ColumnName.USERS_EMAIL));
                user.setRole(role);
                user.setFirstName(rs.getString(ColumnName.USERS_FIRST_NAME));
                user.setLastName(rs.getString(ColumnName.USERS_LAST_NAME));
                user.setAddress(rs.getString(ColumnName.USERS_ADDRESS));
                user.setPhone(rs.getString(ColumnName.USERS_PHONE));
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

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SIGNUP_SQL)) {
            ps.setString(SignUpIndex.USERNAME, signUpData.getUsername());
            ps.setString(SignUpIndex.EMAIL, signUpData.getEmail());
            ps.setString(SignUpIndex.PASSWORD, passwordEncryptor.getHash(signUpData.getPassword()));
            ps.setInt(SignUpIndex.ROLE, STATUS_USER);
            ps.setString(SignUpIndex.FIRST_NAME, signUpData.getFirstName());
            ps.setString(SignUpIndex.LAST_NAME, signUpData.getLastName());
            ps.setString(SignUpIndex.ADDRESS, signUpData.getAddress());
            ps.setString(SignUpIndex.PHONE, signUpData.getPhoneNumber());
            ps.execute();
            return ResultCode.SUCCESS;

        } catch (SQLException e) {
            if (e.getErrorCode() == DUBLICATE_EMAIL_ERROR_CODE) {
                return ResultCode.ERROR_DUPLICATE_EMAIL;
            } else {
                throw new DAOException(MESSAGE_SIGN_UP_PROBLEM, e);
            }
        }
    }

    @Override
    public User findUserByEmail(String email) throws DAOException {
        User user = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {
            ps.setString(FindUserIndex.EMAIL, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(ColumnName.USERS_ID));
                user.setUsername(resultSet.getString(ColumnName.USERS_USERNAME));
                user.setPassword(resultSet.getString(ColumnName.USERS_PASSWORD));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_IS_EMAIL_EXISTS_PROBLEM, e);
        }
        return user;
    }

    @Override
    public void setPasswordById(Integer id, String password) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_PASSWORD_BY_ID_SQL)) {
            ps.setString(SetPasswordIndex.PASSWORD, passwordEncryptor.getHash(password));
            ps.setInt(SetPasswordIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SET_PASSWORD_PROBLEM, e);
        }
    }

    @Override
    public ResultCode updateUser(User user) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER_BY_ID_SQL)) {
            ps.setString(UpdateIndex.USERNAME, user.getUsername());
            ps.setString(UpdateIndex.PASSWORD, passwordEncryptor.getHash(user.getPassword()));
            ps.setString(UpdateIndex.FIRST_NAME, user.getFirstName());
            ps.setString(UpdateIndex.LAST_NAME, user.getLastName());
            ps.setString(UpdateIndex.ADDRESS, user.getAddress());
            ps.setString(UpdateIndex.PHONE, user.getPhone());
            ps.setInt(UpdateIndex.ID, user.getId());
            ps.execute();
            return ResultCode.SUCCESS;
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_USER_PROBLEM, e);
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
