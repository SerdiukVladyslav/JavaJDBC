package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadInfoMessages {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.connect();

        while (true) {
            String sql = "SELECT id, message, type, processed FROM notice WHERE type = 'INFO' AND processed = false";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String message = rs.getString("message");

                    System.out.println("INFO: " + message);

                    // Удаление сообщения
                    String deleteSql = "DELETE FROM notice WHERE id = ?";
                    try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                        deletePstmt.setInt(1, id);
                        deletePstmt.executeUpdate();
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
