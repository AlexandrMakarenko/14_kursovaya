package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для обработки неизвестных URL-адресов
 * Перенаправляет их на страницу 404
 */
public class NotFoundServlet extends HttpServlet {
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Перенаправляем на страницу 404, устанавливая правильные атрибуты ошибки
        request.setAttribute("jakarta.servlet.error.status_code", 404);
        request.setAttribute("jakarta.servlet.error.message", "Запрашиваемая страница не существует.");
        request.setAttribute("jakarta.servlet.error.request_uri", request.getRequestURI());
        request.getRequestDispatcher("/WEB-INF/views/error404.jsp").forward(request, response);
    }
} 