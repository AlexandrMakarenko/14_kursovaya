package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для обработки ошибок и перенаправления запросов на соответствующие страницы ошибок
 */
@WebServlet("/error-handler")
public class ErrorHandlerServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processError(request, response);
    }
    
    /**
     * Обрабатывает ошибку и перенаправляет на соответствующую страницу ошибки
     */
    private void processError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Получаем код ошибки и исключение из атрибутов запроса
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        String servletName = (String) request.getAttribute("jakarta.servlet.error.servlet_name");
        String requestURI = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        
        // Проверяем, есть ли параметр типа ошибки в URL
        String errorType = request.getParameter("type");
        if (errorType != null && !errorType.isEmpty()) {
            try {
                statusCode = Integer.parseInt(errorType);
            } catch (NumberFormatException e) {
                // Если параметр не является числом, игнорируем его
            }
        }
        
        // Записываем информацию об ошибке в лог
        if (throwable != null) {
            log("Ошибка при запросе: " + requestURI, throwable);
        } else {
            log("Ошибка " + statusCode + " при запросе: " + requestURI + " (сервлет: " + servletName + ")");
        }
        
        // Перенаправляем на соответствующую страницу ошибки
        if (statusCode != null) {
            switch (statusCode) {
                case 404:
                    request.getRequestDispatcher("/WEB-INF/views/error404.jsp").forward(request, response);
                    break;
                case 500:
                    request.getRequestDispatcher("/WEB-INF/views/error500.jsp").forward(request, response);
                    break;
                case 403:
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    break;
            }
        } else {
            // Если код ошибки не определен, отображаем общую страницу ошибки
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
} 