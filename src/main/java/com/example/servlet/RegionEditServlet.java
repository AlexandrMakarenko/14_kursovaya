package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.RegionDAO;
import com.example.model.Region;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для получения данных о регионе для редактирования в модальном окне
 */
@WebServlet("/region-edit")
public class RegionEditServlet extends HttpServlet {
    private RegionDAO regionDAO;
    
    @Override
    public void init() {
        regionDAO = DAOFactory.getInstance().getRegionDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int regionId = Integer.parseInt(request.getParameter("id"));
            Region region = regionDAO.getById(regionId);
            
            if (region != null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("id", region.getId());
                jsonResponse.put("description", region.getDescription());
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Регион не найден");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Ошибка при получении данных региона: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Некорректный ID региона");
        }
    }
} 