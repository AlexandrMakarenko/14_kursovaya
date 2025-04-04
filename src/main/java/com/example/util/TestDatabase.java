package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestDatabase {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/employee";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "2222");
        
        System.out.println("Попытка подключения к базе данных...");
        System.out.println("URL: " + url);
        System.out.println("Пользователь: postgres");
        
        try {
            // Загрузка драйвера
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер PostgreSQL успешно загружен");
            
            // Попытка подключения
            Connection conn = DriverManager.getConnection(url, props);
            System.out.println("Подключение успешно установлено!");
            conn.close();
            System.out.println("Подключение закрыто.");
            
            // Второй способ подключения
            System.out.println("\nПроверка вторым способом:");
            Connection conn2 = DriverManager.getConnection(url, "postgres", "2222");
            System.out.println("Подключение 2 успешно установлено!");
            conn2.close();
            System.out.println("Подключение 2 закрыто.");
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке драйвера PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        }
    }
} 