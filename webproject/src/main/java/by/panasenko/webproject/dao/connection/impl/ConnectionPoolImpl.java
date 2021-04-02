package by.panasenko.webproject.dao.connection.impl;

import by.panasenko.webproject.dao.connection.ConnectionPool;
import by.panasenko.webproject.exception.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolImpl implements ConnectionPool {
    static final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);
    private static final ConnectionPoolImpl instance = new ConnectionPoolImpl();
    private static final Properties properties = new Properties();
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final int CONNECTION_COUNT = 5;

    private final String DB_PROPERTIES = "db.properties";
    private final String DB_URL = "db.url";
    private final String DB_USER = "db.user";
    private final String DB_PASSWORD = "db.password";

    private final String MESSAGE_DRIVER_NOT_FOUND = "Driver not found";
    private final String MESSAGE_SQL_PROBLEM = "Sql connection problem";
    private final String MESSAGE_NO_FREE_CONNECTION = "No free connection";
    private final String MESSAGE_CONNECTION_CLOSING_PROBLEM = "Closing connection problem.";

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> activeConnections;

    private ConnectionPoolImpl() {
        initPool();
    }

    public static ConnectionPoolImpl getInstance() {
        return instance;
    }

    private void initPool() {
        try{
            properties.load(new FileReader(DB_PROPERTIES));
        } catch (IOException e) {
            logger.error("fatal error: config file not found: " + e);
            throw new RuntimeException("fatal error: config file not found: " + e);
        }
        String url = (String) properties.get(DB_URL);
        String user = (String) properties.get(DB_USER);
        String password = (String) properties.get(DB_PASSWORD);

        freeConnections = new ArrayBlockingQueue<>(CONNECTION_COUNT);
        activeConnections = new ArrayBlockingQueue<>(CONNECTION_COUNT);

        try {
            Class.forName(DRIVER);
            for (int count = 0; count < CONNECTION_COUNT; count++) {
                Connection connection;
                connection = DriverManager.getConnection(url, user, password);
                freeConnections.add(connection);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(MESSAGE_DRIVER_NOT_FOUND, e);

        } catch (SQLException e) {
            throw new RuntimeException(MESSAGE_SQL_PROBLEM, e);
        }
    }

    @Override
    public Connection getConnection() throws DAOException {
        Connection connection;
        connection = freeConnections.poll();
        if (connection == null) {
            throw new DAOException(MESSAGE_NO_FREE_CONNECTION);
        }
        activeConnections.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        activeConnections.remove(connection);
        freeConnections.add(connection);
    }

    public void clearConnectionPool() throws DAOException {
        closeConnectionQueue(freeConnections);
        closeConnectionQueue(activeConnections);
    }

    private void closeConnectionQueue(BlockingQueue<Connection> connectionQueue) throws DAOException {
        Connection connection;
        while ((connection = connectionQueue.poll()) != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(MESSAGE_CONNECTION_CLOSING_PROBLEM, e);
            }
        }
    }

    @Override
    public void closeConnection(Connection connection, PreparedStatement ps) throws DAOException {
        try {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                releaseConnection(connection);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CONNECTION_CLOSING_PROBLEM, e);
        }
    }
}
