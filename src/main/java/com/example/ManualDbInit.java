package com.example;

import com.example.util.DatabaseInitializer;

/**
 * Класс для ручной инициализации базы данных.
 * Запустите этот класс, чтобы создать и заполнить базу данных.
 */
public class ManualDbInit {
    
    /**
     * Главный метод для запуска инициализации базы данных.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     РУЧНАЯ ИНИЦИАЛИЗАЦИЯ БАЗЫ ДАННЫХ");
        System.out.println("==================================================");
        
        try {
            System.out.println("Начинаем процесс инициализации...");
            DatabaseInitializer.initializeDatabase();
            System.out.println("==================================================");
            System.out.println("     ИНИЦИАЛИЗАЦИЯ УСПЕШНО ЗАВЕРШЕНА!");
            System.out.println("==================================================");
        } catch (Exception e) {
            System.err.println("==================================================");
            System.err.println("     ОШИБКА ПРИ ИНИЦИАЛИЗАЦИИ БАЗЫ ДАННЫХ:");
            System.err.println("==================================================");
            e.printStackTrace();
            System.err.println("==================================================");
            System.exit(1);
        }
    }
} 