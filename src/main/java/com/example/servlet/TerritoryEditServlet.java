package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.TerritoryDAO;
import com.example.model.Territory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для получения данных о территории для редактирования в модальном окне
 */
@WebServlet("/territory-edit")
public class TerritoryEditServlet extends HttpServlet {
    private TerritoryDAO territoryDAO;
    
    @Override
    public void init() {
        territoryDAO = DAOFactory.getInstance().getTerritoryDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int territoryId = Integer.parseInt(request.getParameter("id"));
            Territory territory = territoryDAO.getById(territoryId);
            
            if (territory != null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("id", territory.getId());
                jsonResponse.put("description", territory.getDescription());
                jsonResponse.put("regionId", territory.getRegionId());
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Территория не найдена");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Ошибка при получении данных территории: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Некорректный ID территории");
        }
    }
} 