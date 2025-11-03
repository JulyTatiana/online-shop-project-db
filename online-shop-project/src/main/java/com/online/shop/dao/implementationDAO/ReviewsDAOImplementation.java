package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IReviewsDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static java.sql.DriverManager.getConnection;

public class ReviewsDAOImplementation extends BaseDAO<Reviews, Integer> implements IReviewsDAO {
    @Override
    public Reviews findById(Integer reviewId) {
        String SQl = "SELECT * FROM reviews WHERE review_id = ?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConn();
            pst = conn.prepareStatement(SQl);
            pst.setInt(1, reviewId);
            var rs = pst.executeQuery();
            if (rs.next()) {
                Reviews review = new Reviews();
                review.setReviewId(rs.getInt("review_id"));
                review.setProductId(rs.getInt("product_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                return review;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pst, null);
        }
        return null;
    }

    @Override
    public boolean insert(Reviews obj) {
        String SQL = "INSERT INTO reviews (product_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConn();
            pst = conn.prepareStatement(SQL);
            pst.setInt(1, obj.getProductId());
            pst.setInt(2, obj.getUserId());
            pst.setInt(3, obj.getRating());
            pst.setString(4, obj.getComment());
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pst, null);
        }
    }

    @Override
    public boolean update(Reviews obj) {
        String SQL = "UPDATE reviews SET product_id = ?, user_id = ?, rating = ?, comment = ? WHERE review_id = ?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConn();
            pst = conn.prepareStatement(SQL);
            pst.setInt(1, obj.getProductId());
            pst.setInt(2, obj.getUserId());
            pst.setInt(3, obj.getRating());
            pst.setString(4, obj.getComment());
            pst.setInt(5, obj.getReviewId());
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pst, null);
        }
    }

    @Override
    public boolean delete(Integer key) {
        String SQL = "DELETE FROM reviews WHERE review_id = ?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConn();
            pst = conn.prepareStatement(SQL);
            pst.setInt(1, key);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pst, null);
        }
    }
}
