package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Временно выводим HTML напрямую вместо JSP
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Система управления территориями</title>");
        out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container mt-5\">");
        out.println("<h1 class=\"mb-4\">Система управления территориями</h1>");
        out.println("<div class=\"alert alert-info\">");
        out.println("<a href=\"test-page\" class=\"alert-link\">Открыть тестовую страницу</a>");
        out.println("</div>");
        out.println("<div class=\"row\">");
        
        // Регионы
        out.println("<div class=\"col-md-4\">");
        out.println("<div class=\"card mb-4\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Регионы</h5>");
        out.println("<p class=\"card-text\">Управление регионами и их описанием</p>");
        out.println("<a href=\"regions\" class=\"btn btn-primary\">Перейти</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        
        // Территории
        out.println("<div class=\"col-md-4\">");
        out.println("<div class=\"card mb-4\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Территории</h5>");
        out.println("<p class=\"card-text\">Управление территориями и их привязкой к регионам</p>");
        out.println("<a href=\"territories\" class=\"btn btn-primary\">Перейти</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        
        // Представители
        out.println("<div class=\"col-md-4\">");
        out.println("<div class=\"card mb-4\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Представители</h5>");
        out.println("<p class=\"card-text\">Управление представителями и их контактной информацией</p>");
        out.println("<a href=\"representatives\" class=\"btn btn-primary\">Перейти</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        
        // Назначение территорий
        out.println("<div class=\"col-md-4\">");
        out.println("<div class=\"card mb-4\">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">Назначение территорий</h5>");
        out.println("<p class=\"card-text\">Управление назначением территорий представителям</p>");
        out.println("<a href=\"representative-territories\" class=\"btn btn-primary\">Перейти</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        
        out.println("</div>"); // row
        out.println("</div>"); // container
        out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }
} 