package com.example.listener;

import com.example.util.DatabaseInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Слушатель, который инициализирует базу данных при запуске приложения
 */
@WebListener
public class DatabaseInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("========================================================");
        System.out.println("Инициализация контекста приложения. Проверка базы данных...");
        
        try {
            // Явно инициализируем базу данных
            // Это должно выполниться до обращения любых сервлетов к базе данных
            System.out.println("Запускаем инициализацию базы данных...");
            DatabaseInitializer.initializeDatabase();
            System.out.println("Инициализация базы данных завершена успешно.");
        } catch (Exception e) {
            System.err.println("КРИТИЧЕСКАЯ ОШИБКА: Не удалось инициализировать базу данных!");
            System.err.println("Причина: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Инициализация контекста приложения завершена.");
        System.out.println("========================================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Ничего не делаем при остановке приложения
    }
} 