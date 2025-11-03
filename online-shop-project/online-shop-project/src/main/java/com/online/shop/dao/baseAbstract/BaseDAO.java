package com.online.shop.dao.baseAbstract;

import com.online.shop.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T, K> {
    protected Connection getConn() throws SQLException, InterruptedException {
        return ConnectionPool.getInstance().getConnection();
    }

    protected void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Failed to close ResultSet: " + e.getMessage());
        }
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Failed to close PreparedStatement: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to close Connection: " + e.getMessage());
        }
    }

    public abstract T findById(K key);

    public abstract boolean insert(T obj);

    public abstract boolean update(T obj);

    public abstract boolean delete(K key);

    public List<T> findAll() {
        throw new UnsupportedOperationException("findAll not implemented");
    }
}
