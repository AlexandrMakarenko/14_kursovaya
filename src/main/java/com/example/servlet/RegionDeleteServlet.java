package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.RegionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для удаления региона
 */
@WebServlet("/region-delete")
public class RegionDeleteServlet extends HttpServlet {
    private RegionDAO regionDAO;
    
    @Override
    public void init() {
        regionDAO = DAOFactory.getInstance().getRegionDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int regionId = Integer.parseInt(request.getParameter("id"));
            boolean success = regionDAO.delete(regionId);
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", success);
            
            if (success) {
                jsonResponse.put("message", "Регион успешно удален");
            } else {
                jsonResponse.put("message", "Не удалось удалить регион. Возможно, регион используется в других записях.");
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Ошибка при удалении региона: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Некорректный ID региона");
            response.getWriter().write(errorResponse.toString());
        }
    }
} 