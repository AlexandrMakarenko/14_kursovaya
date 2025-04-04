package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Класс для прямого тестирования подключения к PostgreSQL
 * без использования нашего класса DatabaseConnection
 */
public class DbTestDirect {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("  ПРЯМОЕ ТЕСТИРОВАНИЕ ПОДКЛЮЧЕНИЯ К POSTGRESQL");
        System.out.println("============================================");
        
        // Параметры подключения к базе данных
        String url = "jdbc:postgresql://localhost:5432/postgres"; // Подключаемся к стандартной БД postgres
        String user = "postgres";
        String password = "2222";

        try {
            // Загружаем драйвер PostgreSQL
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер PostgreSQL успешно загружен");
            
            System.out.println("\nПараметры подключения:");
            System.out.println("URL: " + url);
            System.out.println("Пользователь: " + user);
            System.out.println("Пароль: " + password);
            
            // Пытаемся установить соединение
            System.out.println("\nУстанавливаем соединение...");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Соединение успешно установлено!");
                
                // Получаем версию PostgreSQL
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT version()")) {
                    if (rs.next()) {
                        System.out.println("\nВерсия PostgreSQL: " + rs.getString(1));
                    }
                }
                
                // Проверяем существование базы данных employee
                boolean employeeDbExists = false;
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(
                             "SELECT 1 FROM pg_database WHERE datname = 'employee'")) {
                    employeeDbExists = rs.next();
                }
                
                if (employeeDbExists) {
                    System.out.println("\nБаза данных 'employee' уже существует");
                } else {
                    System.out.println("\nБаза данных 'employee' не существует. Создаем...");
                    try (Statement stmt = conn.createStatement()) {
                        stmt.executeUpdate("CREATE DATABASE employee");
                        System.out.println("База данных 'employee' успешно создана");
                    }
                }
                
                System.out.println("\nСоединение успешно закрыто");
            }
            
            System.out.println("\n============================================");
            System.out.println("  ТЕСТИРОВАНИЕ ЗАВЕРШЕНО УСПЕШНО");
            System.out.println("============================================");
            
        } catch (Exception e) {
            System.err.println("\nОШИБКА: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 