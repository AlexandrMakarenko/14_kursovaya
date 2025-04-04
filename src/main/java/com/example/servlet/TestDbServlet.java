package com.example.servlet;

import com.example.util.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet("/test-db")
public class TestDbServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Database Connection Test</title>");
        out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        out.println("</head><body>");
        out.println("<div class=\"container mt-5\">");
        out.println("<h1>Testing Database Connection</h1>");
        
        // Форма для ручного ввода параметров подключения
        out.println("<div class=\"card mb-4\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Введите параметры подключения</h5>");
        out.println("<form method=\"post\" action=\"test-db\" class=\"mb-3\">");
        out.println("<div class=\"mb-3\">");
        out.println("<label for=\"dbUrl\" class=\"form-label\">URL базы данных:</label>");
        out.println("<input type=\"text\" class=\"form-control\" id=\"dbUrl\" name=\"dbUrl\" value=\"jdbc:postgresql://localhost:5432/employee\">");
        out.println("</div>");
        out.println("<div class=\"mb-3\">");
        out.println("<label for=\"dbUser\" class=\"form-label\">Пользователь:</label>");
        out.println("<input type=\"text\" class=\"form-control\" id=\"dbUser\" name=\"dbUser\" value=\"postgres\">");
        out.println("</div>");
        out.println("<div class=\"mb-3\">");
        out.println("<label for=\"dbPassword\" class=\"form-label\">Пароль:</label>");
        out.println("<input type=\"password\" class=\"form-control\" id=\"dbPassword\" name=\"dbPassword\">");
        out.println("</div>");
        out.println("<button type=\"submit\" class=\"btn btn-primary\">Проверить подключение</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        
        // Тестирование стандартного подключения из файла настроек
        out.println("<div class=\"card\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Тест из настроек</h5>");
        
        try {
            // Попытка подключения с настройками из properties файла
            Connection conn = DatabaseConnection.getConnection();
            out.println("<p style='color:green;'>Успешное подключение к базе данных!</p>");
            
            // Закрытие соединения
            conn.close();
        } catch (SQLException e) {
            out.println("<p style='color:red;'>Ошибка подключения к базе данных:</p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace(out);
        }
        
        out.println("</div>");
        out.println("</div>");
        
        out.println("</div>"); // container
        out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\"></script>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String dbUrl = request.getParameter("dbUrl");
        String dbUser = request.getParameter("dbUser");
        String dbPassword = request.getParameter("dbPassword");
        
        out.println("<html><head><title>Database Connection Test Result</title>");
        out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        out.println("</head><body>");
        out.println("<div class=\"container mt-5\">");
        out.println("<h1>Database Connection Test Result</h1>");
        
        out.println("<div class=\"card mb-4\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Параметры подключения</h5>");
        out.println("<p><strong>URL:</strong> " + dbUrl + "</p>");
        out.println("<p><strong>Пользователь:</strong> " + dbUser + "</p>");
        out.println("<p><strong>Пароль:</strong> " + (dbPassword != null && !dbPassword.isEmpty() ? "******" : "не указан") + "</p>");
        
        try {
            // Загрузка драйвера
            Class.forName("org.postgresql.Driver");
            out.println("<p>Драйвер PostgreSQL успешно загружен</p>");
            
            // Попытка подключения с параметрами из формы
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            out.println("<p style='color:green;'>Успешное подключение к базе данных!</p>");
            
            // Закрытие соединения
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("<p style='color:red;'>Ошибка при загрузке драйвера PostgreSQL:</p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            out.println("<p style='color:red;'>Ошибка при подключении к базе данных:</p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            out.println("<p><strong>SQLState:</strong> " + e.getSQLState() + "</p>");
            e.printStackTrace(out);
        }
        
        out.println("</div>");
        out.println("</div>");
        
        out.println("<a href=\"test-db\" class=\"btn btn-primary\">Назад</a>");
        
        out.println("</div>"); // container
        out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\"></script>");
        out.println("</body></html>");
    }
} 