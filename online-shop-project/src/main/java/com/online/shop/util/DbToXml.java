package com.online.shop.util;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbToXml implements Exporter {

    private static final String ADDRESSES_PATH = "src/main/java/com/online/shop/util/format/xml/addresses.xml";
    private static final String PRODUCTS_PATH = "src/main/java/com/online/shop/util/format/xml/products.xml";

    // -----------------------------
    // EXPORT ADDRESSES
    // -----------------------------
    public void exportAddresses() {
        String sql = "SELECT address_id, street, city, country, zip_code, user_id FROM addresses";
        try (Connection conn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             FileOutputStream fos = new FileOutputStream(ADDRESSES_PATH)) {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(fos, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("addresses");

            while (rs.next()) {
                writer.writeStartElement("address");

                writer.writeStartElement("address_id");
                writer.writeCharacters(String.valueOf(rs.getInt("address_id")));
                writer.writeEndElement();

                writer.writeStartElement("street");
                writer.writeCharacters(rs.getString("street"));
                writer.writeEndElement();

                writer.writeStartElement("city");
                writer.writeCharacters(rs.getString("city"));
                writer.writeEndElement();

                writer.writeStartElement("country");
                writer.writeCharacters(rs.getString("country"));
                writer.writeEndElement();

                writer.writeStartElement("zip_code");
                writer.writeCharacters(rs.getString("zip_code"));
                writer.writeEndElement();

                writer.writeStartElement("user_id");
                writer.writeCharacters(String.valueOf(rs.getInt("user_id")));
                writer.writeEndElement();

                writer.writeEndElement(); // </address>
            }

            writer.writeEndElement(); // </addresses>
            writer.writeEndDocument();
            writer.flush();
            writer.close();

            System.out.println("Exported addresses → " + ADDRESSES_PATH);
        } catch (Exception e) {
            System.err.println("Error exporting addresses:");
            e.printStackTrace();
        }
    }

    // -----------------------------
    // EXPORT PRODUCTS
    // -----------------------------
    public void exportProducts() {
        String sql = "SELECT product_id, name, price, category_id FROM products";
        try (Connection conn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             FileOutputStream fos = new FileOutputStream(PRODUCTS_PATH)) {

            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(fos, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("products");

            while (rs.next()) {
                writer.writeStartElement("product");

                writer.writeStartElement("product_id");
                writer.writeCharacters(String.valueOf(rs.getInt("product_id")));
                writer.writeEndElement();

                writer.writeStartElement("name");
                writer.writeCharacters(rs.getString("name"));
                writer.writeEndElement();

                writer.writeStartElement("price");
                writer.writeCharacters(String.valueOf(rs.getDouble("price")));
                writer.writeEndElement();

                writer.writeStartElement("category_id");
                writer.writeCharacters(String.valueOf(rs.getInt("category_id")));
                writer.writeEndElement();

                writer.writeEndElement(); // </product>
            }

            writer.writeEndElement(); // </products>
            writer.writeEndDocument();
            writer.flush();
            writer.close();

            System.out.println("Exported products → " + PRODUCTS_PATH);
        } catch (Exception e) {
            System.err.println("Error exporting products:");
            e.printStackTrace();
        }
    }

    // -----------------------------
    // EXPORT ALL
    // -----------------------------
    public void exportAll() {
        exportAddresses();
        exportProducts();
    }

    // -----------------------------
    // IMPLEMENTATION OF INTERFACE
    // -----------------------------
    @Override
    public void exportData() {
        exportAll();
    }
}
