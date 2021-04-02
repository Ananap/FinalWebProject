package by.panasenko.webproject.dao.connection;

import by.panasenko.webproject.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface ConnectionPool {
    Connection getConnection() throws DAOException;
    void closeConnection(Connection connection, PreparedStatement ps) throws DAOException;
}
