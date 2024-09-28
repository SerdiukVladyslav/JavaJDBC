package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

public class InsertMessages {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.connect();
        Random random = new Random();

        String[] types = {"INFO", "WARN"};

        while (true) {
            String type = types[random.nextInt(types.length)];
            String message;

            if (type.equals("INFO")) {
                message = "Новое сообщение от " + LocalDateTime.now();
            } else {
                message = "Произошла ошибка в " + LocalDateTime.now();
            }

            boolean processed = false;

            String sql = "INSERT INTO notice (message, type, processed) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, message);
                pstmt.setString(2, type);
                pstmt.setBoolean(3, processed);
                pstmt.executeUpdate();
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
