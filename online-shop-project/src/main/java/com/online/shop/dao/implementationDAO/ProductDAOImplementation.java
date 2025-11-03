package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IProductDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAOImplementation extends BaseDAO<Product, Integer> implements IProductDAO {
    @Override
    public Product findById(Integer productId) {
        String SQL = "SELECT * FROM products WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                return product;
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Error finding product by ID: " + e.getMessage());
        } finally {
            closeResources(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public boolean insert(Product obj) {
        String SQL = "INSERT INTO products (name, description, price) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, obj.getName());
            pstmt.setString(2, obj.getDescription());
            pstmt.setDouble(3, obj.getPrice());
            return pstmt.executeUpdate() == 1;
        } catch (SQLException | InterruptedException e) {
            System.err.println("Error inserting product: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    @Override
    public boolean update(Product obj) {
        String SQL = "UPDATE products SET name = ?, description = ?, price = ? WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, obj.getName());
            pstmt.setString(2, obj.getDescription());
            pstmt.setDouble(3, obj.getPrice());
            pstmt.setInt(4, obj.getProductId());
            return pstmt.executeUpdate() == 1;
        } catch (SQLException | InterruptedException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    @Override
    public boolean delete(Integer key) {
        String SQL = "DELETE FROM products WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, key);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException | InterruptedException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
}
