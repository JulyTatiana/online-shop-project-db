package com.online.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.InputStream;

public class ConnectionPool {
    private static ConnectionPool instance;
    private BlockingQueue<Connection> pool;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool() throws SQLException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(in);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            poolSize = Integer.parseInt(props.getProperty("db.poolSize", "10"));
        } catch (Exception e) {
            throw new SQLException("Failed to load db.properties", e);
        }

        pool = new ArrayBlockingQueue<>(poolSize);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL driver not found", e);
        }

        for (int i = 0; i < poolSize; i++) {
            pool.add(createNewConnection());
        }
    }

    private Connection createNewConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static synchronized ConnectionPool getInstance() throws SQLException {
        if (instance == null) instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection c = pool.take();
            if (c.isClosed()) {
                c = createNewConnection();
            }
            return c;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for a connection", e);
        }
    }

    public void releaseConnection(Connection c) {
        if (c == null) return;
        try {
            if (c.isClosed()) return;
            pool.offer(c);
        } catch (SQLException ignore) {}
    }

    public void shutdown() {
        Connection c;
        while ((c = pool.poll()) != null) {
            try { c.close(); } catch (SQLException ignore) {}
        }
    }
}
