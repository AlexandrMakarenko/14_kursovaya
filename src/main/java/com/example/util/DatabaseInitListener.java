package com.example.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Слушатель инициализации ServletContext для автоматической инициализации базы данных
 * при запуске веб-приложения
 */
@WebListener
public class DatabaseInitListener implements ServletContextListener {
    
    /**
     * Вызывается при инициализации контекста сервлета
     * @param sce событие инициализации
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==================================================");
        System.out.println("     ИНИЦИАЛИЗАЦИЯ БАЗЫ ДАННЫХ ПРИ ЗАПУСКЕ");
        System.out.println("==================================================");
        
        try {
            // Инициализируем базу данных
            DatabaseInitializer.initializeDatabase();
            System.out.println("База данных успешно инициализирована при запуске приложения");
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            e.printStackTrace();
            
            // Добавляем информацию в атрибуты контекста сервлета для доступа из приложения
            sce.getServletContext().setAttribute("dbInitError", e.getMessage());
        }
        
        System.out.println("==================================================");
    }
    
    /**
     * Вызывается при уничтожении контекста сервлета
     * @param sce событие уничтожения
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Ничего не делаем при завершении работы
        System.out.println("Приложение завершает работу");
    }
} 