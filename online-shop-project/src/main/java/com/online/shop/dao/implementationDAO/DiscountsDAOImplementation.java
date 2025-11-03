package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IDiscountsDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Discounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiscountsDAOImplementation extends BaseDAO<Discounts, Integer> implements IDiscountsDAO {
    @Override
    public Discounts findById(Integer discountId) {
        String SQL = "SELECT * FROM discounts WHERE discount_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, discountId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Discounts discount = new Discounts();
                discount.setDiscountId(rs.getInt("discount_id"));
                discount.setDescription(rs.getString("description"));
                discount.setPercentage(rs.getDouble("percentage"));
                return discount;
            }
        } catch (Exception e) {
            System.err.println("findById error: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;

    }

    @Override
    public boolean insert(Discounts obj) {
        String SQL = "INSERT INTO discounts (description, percentage) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, obj.getDescription());
            ps.setDouble(2, obj.getPercentage());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("insert error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean update(Discounts obj) {
        String SQL = "UPDATE discounts SET description = ?, percentage = ? WHERE discount_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, obj.getDescription());
            ps.setDouble(2, obj.getPercentage());
            ps.setInt(3, obj.getDiscountId());
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
        String SQL = "DELETE FROM discounts WHERE discount_id = ?";
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
