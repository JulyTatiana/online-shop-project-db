package com.online.shop.util;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbToJson implements Exporter {

    private static final String USERS_PATH = "src/main/java/com/online/shop/util/format/json/users.json";
    private static final String DISCOUNTS_PATH = "src/main/java/com/online/shop/util/format/json/discounts.json";

    // -----------------------------
    // EXPORT USERS
    // -----------------------------
    public void exportUsers() {
        String sql = "SELECT user_id, name, username, user_type, priority FROM users";
        try (Connection conn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             FileWriter writer = new FileWriter(USERS_PATH)) {

            writer.write("[\n");
            boolean first = true;

            while (rs.next()) {
                if (!first) writer.write(",\n");
                first = false;

                writer.write("  {\n");
                writer.write("    \"user_id\": " + rs.getInt("user_id") + ",\n");
                writer.write("    \"name\": \"" + escape(rs.getString("name")) + "\",\n");
                writer.write("    \"username\": \"" + escape(rs.getString("username")) + "\",\n");
                writer.write("    \"user_type\": \"" + escape(rs.getString("user_type")) + "\",\n");
                writer.write("    \"priority\": " + rs.getInt("priority") + "\n");
                writer.write("  }");
            }

            writer.write("\n]");
            System.out.println("✅ Exported users → " + USERS_PATH);

        } catch (Exception e) {
            System.err.println("❌ Error exporting users:");
            e.printStackTrace();
        }
    }

    // -----------------------------
    // EXPORT DISCOUNTS
    // -----------------------------
    public void exportDiscounts() {
        String sql = "SELECT discount_id, code, percentage, minimum_purchase, description FROM discounts";
        try (Connection conn = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             FileWriter writer = new FileWriter(DISCOUNTS_PATH)) {

            writer.write("[\n");
            boolean first = true;

            while (rs.next()) {
                if (!first) writer.write(",\n");
                first = false;

                writer.write("  {\n");
                writer.write("    \"discount_id\": " + rs.getInt("discount_id") + ",\n");
                writer.write("    \"code\": \"" + escape(rs.getString("code")) + "\",\n");
                writer.write("    \"percentage\": " + rs.getDouble("percentage") + ",\n");
                writer.write("    \"minimum_purchase\": " + rs.getDouble("minimum_purchase") + ",\n");
                writer.write("    \"description\": \"" + escape(rs.getString("description")) + "\"\n");
                writer.write("  }");
            }

            writer.write("\n]");
            System.out.println("✅ Exported discounts → " + DISCOUNTS_PATH);

        } catch (Exception e) {
            System.err.println("❌ Error exporting discounts:");
            e.printStackTrace();
        }
    }

    // -----------------------------
    // ESCAPE SPECIAL CHARACTERS
    // -----------------------------
    private String escape(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    // -----------------------------
    // EXPORT ALL
    // -----------------------------
    public void exportAll() {
        exportUsers();
        exportDiscounts();
    }

    @Override
    public void exportData() {
        exportAll();
    }
}
