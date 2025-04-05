package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.TerritoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для удаления территории
 */
@WebServlet("/territory-delete")
public class TerritoryDeleteServlet extends HttpServlet {
    private TerritoryDAO territoryDAO;
    
    @Override
    public void init() {
        territoryDAO = DAOFactory.getInstance().getTerritoryDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int territoryId = Integer.parseInt(request.getParameter("id"));
            boolean success = territoryDAO.delete(territoryId);
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", success);
            
            if (success) {
                jsonResponse.put("message", "Территория успешно удалена");
            } else {
                jsonResponse.put("message", "Не удалось удалить территорию. Возможно, территория используется в других записях.");
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Ошибка при удалении территории: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Некорректный ID территории");
            response.getWriter().write(errorResponse.toString());
        }
    }
} 