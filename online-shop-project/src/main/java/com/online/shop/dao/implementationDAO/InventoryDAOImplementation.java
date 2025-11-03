package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IInventoryDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryDAOImplementation extends BaseDAO<Inventory, Integer> implements IInventoryDAO {
    @Override
    public Inventory findByProductId(Integer productId) {
        String SQL = "SELECT * FROM inventories WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setProductId(rs.getInt("product_id"));
                inventory.setStock(rs.getInt("stock"));
                inventory.setReserved(rs.getInt("reserved"));
                return inventory;
            }
        } catch (Exception e) {
            System.err.println("findByProductId error: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;

    }

    @Override
    public Inventory findById(Integer key) {
        return null;
    }

    @Override
    public boolean insert(Inventory obj) {
        String SQL = "INSERT INTO inventories (product_id, stock, reserved) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, obj.getProductId());
            ps.setInt(2, obj.getStock());
            ps.setInt(3, obj.getReserved());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("insert error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean update(Inventory obj) {
        String SQL = "UPDATE inventories SET stock = ?, reserved = ? WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, obj.getStock());
            ps.setInt(2, obj.getReserved());
            ps.setInt(3, obj.getProductId());
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
        String SQL = "DELETE FROM inventories WHERE product_id = ?";
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
