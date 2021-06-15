package by.panasenko.webproject.controller.listener;

import by.panasenko.webproject.exception.ConnectionPoolException;
import by.panasenko.webproject.model.connection.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionPoolException e) {
            logger.fatal("connection pool destruction error", e);
            throw new RuntimeException("connection pool destruction error", e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().initPool();
        } catch (ConnectionPoolException e) {
            logger.fatal("connection pool initialization error", e);
            throw new RuntimeException("connection pool initialization error", e);
        }
    }
}
