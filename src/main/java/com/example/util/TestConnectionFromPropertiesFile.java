package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnectionFromPropertiesFile {
    public static void main(String[] args) {
        // Путь к файлу настроек
        String configFile = "src/main/resources/database.properties";
        Properties properties = new Properties();
        
        System.out.println("Тест подключения к базе данных из файла настроек");
        System.out.println("Файл настроек: " + configFile);
        
        try {
            // Проверка наличия файла
            Path configPath = Paths.get(configFile);
            if (!Files.exists(configPath)) {
                System.err.println("Файл настроек не найден: " + configFile);
                return;
            }
            
            // Вывод содержимого файла для проверки
            System.out.println("\nСодержимое файла настроек:");
            Files.lines(configPath).forEach(System.out::println);
            System.out.println();
            
            // Загрузка свойств из файла
            try (InputStream input = new FileInputStream(configFile)) {
                properties.load(input);
                
                // Вывод загруженных параметров
                System.out.println("Загруженные параметры:");
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    System.out.println(key + " = " + (key.contains("password") ? "*****" : value));
                    
                    // Обрезаем пробелы
                    if (value != null) {
                        properties.setProperty(key, value.trim());
                    }
                }
                
                // Подготовка параметров подключения
                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");
                
                if (url == null || username == null || password == null) {
                    System.err.println("Не все параметры подключения указаны в файле настроек.");
                    return;
                }
                
                // Байты пароля для анализа проблем
                System.out.println("\nАнализ пароля:");
                System.out.print("Байты пароля: ");
                for (byte b : password.getBytes()) {
                    System.out.print(b + " ");
                }
                System.out.println("\nДлина пароля: " + password.length());
                
                // Загрузка драйвера
                Class.forName("org.postgresql.Driver");
                System.out.println("\nДрайвер PostgreSQL успешно загружен");
                
                // Попытка подключения через Properties
                System.out.println("\nПопытка подключения через Properties:");
                Properties connectionProps = new Properties();
                connectionProps.setProperty("user", username);
                connectionProps.setProperty("password", password);
                
                Connection conn1 = DriverManager.getConnection(url, connectionProps);
                System.out.println("Подключение успешно установлено!");
                conn1.close();
                
                // Попытка подключения через прямые параметры
                System.out.println("\nПопытка подключения через прямые параметры:");
                Connection conn2 = DriverManager.getConnection(url, username, password);
                System.out.println("Подключение успешно установлено!");
                conn2.close();
                
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла настроек: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке драйвера PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 