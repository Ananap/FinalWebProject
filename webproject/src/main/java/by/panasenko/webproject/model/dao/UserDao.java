package by.panasenko.webproject.model.dao;

import by.panasenko.webproject.entity.SignInData;
import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.exception.DaoException;

import java.util.Optional;

/**
 * Interface provides methods to interact with Users data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface UserDao {

    /**
     * Connects to database, checks the credentials and returns an User object if success.
     *
     * @param signInData is Object of {@link SignInData}, which contains information about user's email and password.
     * @return optional of {@link User} if user's data exists and password matches, empty Optional if user's email and password are not correct.
     * @throws DaoException when problems with database connection occurs.
     */
    Optional<User> signIn(SignInData signInData) throws DaoException;

    /**
     * Connects to database, creates new user by data provided and returns {@link ResultCode} object as result.
     *
     * @param signUpData Object of {@link SignUpData}, which contains user information.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DaoException when problems with database connection occurs.
     */
    ResultCode signUp(SignUpData signUpData) throws DaoException;


    /**
     * Connects to database and returns {@link User} object by email as result.
     *
     * @param email is text contains email of user.
     * @return {@link User} if user's email exists.
     * @throws DaoException when problems with database connection occurs.
     */
    User findUserByEmail(String email) throws DaoException;

    /**
     * Connects to database and sets new password to user by ID.
     *
     * @param id       is user's ID value.
     * @param password is text that contains new password.
     * @throws DaoException when problems with database connection occurs.
     */
    void setPasswordById(Integer id, String password) throws DaoException;

    /**
     * Connects to database, update user's data and returns {@link ResultCode} object
     *
     * @param user is Object of {@link User}, which contains full information about user.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DaoException when problems with database connection occurs.
     */
    ResultCode updateUser(User user) throws DaoException;
}
