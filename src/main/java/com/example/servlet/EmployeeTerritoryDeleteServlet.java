package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.EmployeeTerritoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для удаления связи сотрудник-территория
 */
@WebServlet("/employeeterritory-delete")
public class EmployeeTerritoryDeleteServlet extends HttpServlet {
    private EmployeeTerritoryDAO employeeTerritoryDAO;
    
    @Override
    public void init() {
        employeeTerritoryDAO = DAOFactory.getInstance().getEmployeeTerritoryDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            int territoryId = Integer.parseInt(request.getParameter("territoryId"));
            
            boolean success = employeeTerritoryDAO.delete(employeeId, territoryId);
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", success);
            
            if (success) {
                jsonResponse.put("message", "Связь успешно удалена");
            } else {
                jsonResponse.put("message", "Не удалось удалить связь. Возможно, запись не существует.");
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Ошибка при удалении связи: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Некорректные параметры запроса");
            response.getWriter().write(errorResponse.toString());
        }
    }
} 