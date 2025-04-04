package com.example;

import com.example.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Класс для тестирования подключения через наш класс DatabaseConnection
 */
public class DbTestApp {
    
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("  ТЕСТИРОВАНИЕ ПОДКЛЮЧЕНИЯ ЧЕРЕЗ DatabaseConnection");
        System.out.println("============================================");
        
        Connection connection = null;
        try {
            // Получаем соединение через наш класс
            System.out.println("Получаем соединение...");
            connection = DatabaseConnection.getConnection();
            System.out.println("Соединение успешно получено!");
            
            // Проверяем соединение, запрашивая версию PostgreSQL
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT version()")) {
                if (rs.next()) {
                    System.out.println("\nВерсия PostgreSQL: " + rs.getString(1));
                }
            }
            
            // Проверяем наличие таблиц
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'")) {
                
                System.out.println("\nТаблицы в базе данных:");
                boolean hasTables = false;
                while (rs.next()) {
                    hasTables = true;
                    System.out.println(" - " + rs.getString("table_name"));
                }
                
                if (!hasTables) {
                    System.out.println("Таблицы не найдены");
                }
            }
            
            System.out.println("\nТест успешно завершен!");
            
        } catch (Exception e) {
            System.err.println("\nОШИБКА: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Закрываем соединение
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Соединение закрыто");
                } catch (Exception e) {
                    System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
                }
            }
        }
        
        System.out.println("============================================");
    }
} 