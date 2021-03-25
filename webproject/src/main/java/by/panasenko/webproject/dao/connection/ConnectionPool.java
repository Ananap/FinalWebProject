package by.panasenko.webproject.dao.connection;

import by.panasenko.webproject.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws SQLException;
    void closeConnection(Connection connection) throws DAOException;
}
