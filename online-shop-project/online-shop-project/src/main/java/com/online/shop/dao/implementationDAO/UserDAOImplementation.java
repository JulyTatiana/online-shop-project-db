package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IUserDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImplementation extends BaseDAO<User, Integer> implements IUserDAO {
    @Override
    public User findById(Integer userId) {
        String SQL = "SELECT * FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                return user;
            }
        } catch (Exception e) {
            System.err.println("findById error: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    @Override
    public boolean insert(User obj) {
        String SQL = "INSERT INTO users (username, password) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, obj.getUsername());
            ps.setString(2, obj.getPassword());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("insert error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean update(User obj) {
        String SQL = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, obj.getUsername());
            ps.setString(2, obj.getPassword());
            ps.setInt(3, obj.getUserId());
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
        String SQL = "DELETE FROM users WHERE user_id = ?";
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
