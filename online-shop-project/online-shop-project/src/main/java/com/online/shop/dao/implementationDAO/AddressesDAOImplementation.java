package com.online.shop.dao.implementationDAO;

import com.online.shop.dao.IAddressesDAO;
import com.online.shop.dao.baseAbstract.BaseDAO;
import com.online.shop.model.Addresses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddressesDAOImplementation extends BaseDAO<Addresses, Integer> implements IAddressesDAO {

    @Override
    public Addresses findById(Integer addressId) {
        String SQl = "SELECT * FROM addresses WHERE address_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQl);
            ps.setInt(1, addressId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Addresses address = new Addresses();
                address.setAddressId(rs.getInt("address_id"));
                address.setStreet(rs.getString("street"));
                address.setCity(rs.getString("city"));
                return address;
            }
        } catch (Exception e) {
            System.err.println("findById error: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }

        return null;
    }

    @Override
    public boolean insert(Addresses address) {
        String SQL = "INSERT INTO addresses (street, city) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("insert error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean update(Addresses address) {
        String SQL = "UPDATE addresses SET street = ?, city = ? WHERE address_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setInt(3, address.getAddressId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("update error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean delete(Integer addressId) {
        String SQL = "DELETE FROM addresses WHERE address_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConn();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, addressId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.err.println("delete error: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }
}
