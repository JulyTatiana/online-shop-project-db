package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IPaymentDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAOImplementation extends BaseDAO<Payment, Integer> implements IPaymentDAO {
    @Override
    public Payment findById(Integer paymentId) {
        String SQL = "SELECT * FROM payments WHERE payment_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, paymentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                return payment;
            }
        } catch (Exception e) {
            System.err.println("findById error: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    @Override
    public boolean insert(Payment obj) {
        String SQL = "INSERT INTO payments (order_id) VALUES (?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, obj.getOrderId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("insert error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean update(Payment obj) {
        String SQL = "UPDATE payments SET order_id = ? WHERE payment_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, obj.getOrderId());
            ps.setInt(2, obj.getPaymentId());
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
        String SQL = "DELETE FROM payments WHERE payment_id = ?";
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
}
