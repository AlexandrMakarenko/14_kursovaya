package com.example.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Класс для работы с подключением к базе данных
 */
public class DatabaseConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/employee";
    private static final String DEFAULT_USERNAME = "postgres";
    private static final String DEFAULT_PASSWORD = "postgres";
    
    // Статический блок инициализации для загрузки драйвера
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC драйвер успешно загружен");
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке PostgreSQL JDBC драйвера: " + e.getMessage());
            throw new RuntimeException("Не удалось загрузить JDBC драйвер PostgreSQL", e);
        }
    }
    
    // Статический метод для получения подключения к базе данных
    public static Connection getConnection() throws SQLException {
        // Загружаем настройки из файла с обрезкой пробелов
        Properties properties;
        try {
            properties = PropertiesLoader.loadProperties("database.properties");
        } catch (IOException e) {
            System.err.println("Не удалось загрузить настройки из database.properties: " + e.getMessage());
            System.err.println("Используем настройки по умолчанию.");
            
            // Создаем дефолтные настройки
            properties = new Properties();
            properties.setProperty("db.url", DEFAULT_URL);
            properties.setProperty("db.username", DEFAULT_USERNAME);
            properties.setProperty("db.password", DEFAULT_PASSWORD);
        }
        
        // Получаем параметры подключения
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        
        // Выводим отладочную информацию (не логины/пароли в продакшн!)
        System.out.println("=== ОТЛАДОЧНАЯ ИНФОРМАЦИЯ ПОДКЛЮЧЕНИЯ ===");
        System.out.println("URL: '" + url + "'");
        System.out.println("Пользователь: '" + username + "'");
        System.out.println("Длина пароля: " + password.length() + " символов");
        System.out.println("Пароль (для отладки): '" + password + "'");
        System.out.println("======================================");
        
        // Пытаемся подключиться напрямую
        try {
            System.out.println("Попытка подключения к: " + url);
            
            // Создаем Properties для передачи параметров подключения
            Properties connectionProps = new Properties();
            connectionProps.setProperty("user", username);
            connectionProps.setProperty("password", password);
            connectionProps.setProperty("ssl", "false");
            connectionProps.setProperty("ApplicationName", "TerritoryManagement");
            
            return DriverManager.getConnection(url, connectionProps);
        } catch (SQLException e) {
            // Если база не существует, пытаемся создать её
            if (e.getSQLState() != null && e.getSQLState().equals("3D000")) { // Код состояния PostgreSQL для "база данных не существует"
                System.out.println("База данных не существует. Пытаемся создать...");
                createDatabase(url, username, password);
                
                // После создания инициализируем базу
                DatabaseInitializer.initializeDatabase();
                
                // Повторяем попытку подключения
                return DriverManager.getConnection(url, username, password);
            } else {
                // Другая ошибка - выводим дополнительную информацию и перебрасываем исключение
                System.err.println("Ошибка подключения к БД: " + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                e.printStackTrace();
                throw e;
            }
        }
    }
    
    /**
     * Создаёт базу данных, если она не существует
     */
    private static void createDatabase(String url, String username, String password) throws SQLException {
        // Извлекаем имя базы данных из URL
        String dbName = "employee"; // имя по умолчанию
        
        if (url.contains("/")) {
            String[] parts = url.split("/");
            if (parts.length > 3) {
                dbName = parts[3].split("\\?")[0]; // получаем имя базы данных из URL
            }
        }
        
        // Формируем URL для подключения к PostgreSQL без указания базы данных
        String postgresUrl = url.substring(0, url.lastIndexOf("/") + 1) + "postgres";
        
        try (Connection conn = DriverManager.getConnection(postgresUrl, username, password);
             Statement stmt = conn.createStatement()) {
            // Проверяем, существует ли база данных
            stmt.execute("CREATE DATABASE " + dbName);
            System.out.println("База данных " + dbName + " успешно создана");
        }
    }
    
    /**
     * Закрывает соединение с базой данных
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
    }
} 