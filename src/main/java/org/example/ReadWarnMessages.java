package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadWarnMessages {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.connect();

        while (true) {
            String sql = "SELECT id, message, type, processed FROM notice WHERE type = 'WARN' AND processed = false";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String message = rs.getString("message");

                    System.out.println("WARN: " + message);

                    // Обновление processed на false
                    String updateSql = "UPDATE notice SET processed = false WHERE id = ?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                        updatePstmt.setInt(1, id);
                        updatePstmt.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);  // Пауза на 1000мс
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
