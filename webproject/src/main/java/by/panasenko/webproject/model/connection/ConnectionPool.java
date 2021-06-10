package by.panasenko.webproject.model.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int DEFAULT_POOL_SIZE = 7;
    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> usedConnections;

    private ConnectionPool() {
        usedConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException e) {
                logger.error("can't create connection with exception: {}", e);
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("can't create connections, empty pool");
            throw new RuntimeException("can't create connections, empty pool");
        }
    }

    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            lock.lock();
            if (instance == null) {
                instance = new ConnectionPool();
                isPoolCreated.getAndSet(true);
            }
            lock.unlock();
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = freeConnections.take();
            usedConnections.put(proxyConnection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return proxyConnection;
    }

    public void releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            logger.error("wild connection is detected");
            throw new RuntimeException("wild connection is detected : " + connection);
        }
        usedConnections.remove(connection);
        try {
            freeConnections.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException | SQLException e) {
                logger.warn("Connection is not deleted");
            }
        }
        deregisterDrivers();
    }


    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("error deregisterDriver with driver: " + driver, e);
            }
        }
    }
}
