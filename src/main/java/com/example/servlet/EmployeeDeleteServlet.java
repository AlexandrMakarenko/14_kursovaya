package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.EmployeeDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для удаления сотрудника
 */
@WebServlet("/employee-delete")
public class EmployeeDeleteServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    
    @Override
    public void init() {
        employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            boolean success = employeeDAO.delete(employeeId);
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", success);
            
            if (success) {
                jsonResponse.put("message", "Сотрудник успешно удален");
            } else {
                jsonResponse.put("message", "Не удалось удалить сотрудника. Возможно, сотрудник используется в других записях.");
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Ошибка при удалении сотрудника: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Некорректный ID сотрудника");
            response.getWriter().write(errorResponse.toString());
        }
    }
} 