package com.example;

import com.example.util.DatabaseConnection;
import com.example.util.DatabaseInitializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Простой класс для тестирования подключения к базе данных
 */
public class DbTest {
    
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     ТЕСТИРОВАНИЕ ПОДКЛЮЧЕНИЯ К БАЗЕ ДАННЫХ");
        System.out.println("==================================================");
        
        // Сначала инициализируем базу данных, если нужно
        try {
            System.out.println("Шаг 1: Инициализация базы данных");
            DatabaseInitializer.initializeDatabase();
            System.out.println("Инициализация завершена успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        // Затем проверяем подключение и выполняем простой запрос
        Connection connection = null;
        try {
            System.out.println("\nШаг 2: Проверка подключения");
            connection = DatabaseConnection.getConnection();
            System.out.println("Подключение установлено успешно!");
            
            System.out.println("\nШаг 3: Выполнение тестового SQL-запроса");
            try (Statement stmt = connection.createStatement()) {
                // Проверяем информацию о сервере PostgreSQL
                ResultSet rs = stmt.executeQuery("SELECT version()");
                if (rs.next()) {
                    System.out.println("Версия PostgreSQL: " + rs.getString(1));
                }
                
                // Проверяем наличие таблиц
                rs = stmt.executeQuery(
                        "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
                );
                
                System.out.println("\nШаг 4: Список таблиц в базе данных:");
                boolean hasTables = false;
                while (rs.next()) {
                    hasTables = true;
                    System.out.println(" - " + rs.getString("table_name"));
                }
                
                if (!hasTables) {
                    System.out.println("В базе данных нет таблиц. Проверьте инициализацию.");
                }
            }
            
            System.out.println("\n==================================================");
            System.out.println("     ТЕСТИРОВАНИЕ ЗАВЕРШЕНО УСПЕШНО!");
            System.out.println("==================================================");
            
        } catch (SQLException e) {
            System.err.println("\nОШИБКА ПРИ ТЕСТИРОВАНИИ: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            // Закрываем соединение
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Соединение с базой данных закрыто.");
                } catch (SQLException e) {
                    System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
                }
            }
        }
    }
} 