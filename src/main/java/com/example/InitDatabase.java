package com.example;

import com.example.util.DatabaseInitializer;

/**
 * Класс для инициализации базы данных из командной строки
 * Запуск: java -cp target/your-application.jar com.example.InitDatabase
 */
public class InitDatabase {
    public static void main(String[] args) {
        System.out.println("Запуск инициализации базы данных...");
        DatabaseInitializer.initializeDatabase();
        System.out.println("Инициализация базы данных завершена.");
    }
} 