package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IOrderDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class OrderDAOImplementation extends BaseDAO<Order, Integer> implements IOrderDAO {
    @Override
    public Order findById(Integer OrderId) {
        String SQL = "SELECT * FROM orders WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs =null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, OrderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                return order;
            }
        } catch (Exception e) {
            System.err.println("findById error: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    @Override
    public boolean insert(Order obj) {
        String SQL = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, obj.getUserId());
            ps.setDouble(2, obj.getTotalAmount());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("insert error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean update(Order obj) {
        String SQL = "UPDATE orders SET user_id = ?, total_amount = ? WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, obj.getUserId());
            ps.setDouble(2, obj.getTotalAmount());
            ps.setInt(3, obj.getOrderId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("update error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean delete(Integer key) {
        String SQL = "DELETE FROM orders WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, key);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("delete error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public Order insert(int userId, Map<Integer, Integer> productQuantities) {
        return null;
    }
}
