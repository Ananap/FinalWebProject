package by.panasenko.webproject.dao.connection.impl;

import by.panasenko.webproject.dao.connection.ConnectionPool;
import by.panasenko.webproject.exception.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPoolImpl implements ConnectionPool {
    static final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);
    private static final ConnectionPoolImpl instance = new ConnectionPoolImpl();
    private static final Properties properties = new Properties();

    private ConnectionPoolImpl() {
        initPool();
    }

    public static ConnectionPoolImpl getInstance() {
        return instance;
    }

    private void initPool() {
        try{
            properties.load(new FileReader("db.properties"));
        } catch (IOException e) {
            logger.error("fatal error: config file not found: " + e);
            throw new RuntimeException("fatal error: config file not found: " + e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        String databaseUrl = (String) properties.get("db.url");
        return DriverManager.getConnection(databaseUrl, properties);
    }

    @Override
    public void closeConnection(Connection connection) throws DAOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Can not close database connection");
        }
    }
}
