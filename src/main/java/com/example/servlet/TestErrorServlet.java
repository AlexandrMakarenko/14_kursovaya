package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для генерации тестовых ошибок разных типов.
 * Используется для проверки работы системы обработки ошибок.
 */
@WebServlet("/test-error")
public class TestErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String errorType = request.getParameter("type");
        
        if (errorType == null) {
            errorType = "general";
        }
        
        switch (errorType) {
            case "404":
                // 404 ошибка (страница не найдена)
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Тестовая ошибка 404 - Страница не найдена");
                break;
                
            case "500":
                // 500 ошибка (внутренняя ошибка сервера)
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Тестовая ошибка 500 - Внутренняя ошибка сервера");
                break;
                
            case "403":
                // 403 ошибка (доступ запрещен)
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Тестовая ошибка 403 - Доступ запрещен");
                break;
                
            case "java":
                // Генерация Java-исключения и отображение страницы ошибки
                try {
                    // Вызываем исключение
                    throw new RuntimeException("Тестовое Java-исключение");
                } catch (Exception e) {
                    request.setAttribute("jakarta.servlet.error.exception", e);
                    request.setAttribute("jakarta.servlet.error.message", e.getMessage());
                    request.setAttribute("jakarta.servlet.error.request_uri", request.getRequestURI());
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
                break;
                
            case "jsp":
                // Перенаправление на JSP, который вызовет ошибку
                request.getRequestDispatcher("/WEB-INF/views/error-trigger.jsp").forward(request, response);
                break;
                
            case "general":
            default:
                // Общая ошибка
                request.setAttribute("errorMessage", "Это тестовая общая ошибка");
                request.setAttribute("jakarta.servlet.error.status_code", 500);
                request.setAttribute("jakarta.servlet.error.message", "Тестовая общая ошибка");
                request.setAttribute("jakarta.servlet.error.request_uri", request.getRequestURI());
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                break;
        }
    }
} 