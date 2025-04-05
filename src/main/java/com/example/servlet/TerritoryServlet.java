package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.RegionDAO;
import com.example.dao.TerritoryDAO;
import com.example.model.Region;
import com.example.model.Territory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/territories")
public class TerritoryServlet extends HttpServlet {
    private TerritoryDAO territoryDAO;
    private RegionDAO regionDAO;
    
    @Override
    public void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        territoryDAO = daoFactory.getTerritoryDAO();
        regionDAO = daoFactory.getRegionDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Получение списка регионов
            List<Region> regions = regionDAO.getAll();
            
            // Получение списка территорий
            List<Territory> territories = territoryDAO.getAll();
            
            request.setAttribute("territories", territories);
            request.setAttribute("regions", regions);
            request.getRequestDispatcher("/WEB-INF/views/territories.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                String description = request.getParameter("description");
                int regionId = Integer.parseInt(request.getParameter("regionId"));
                
                Territory territory = new Territory();
                territory.setDescription(description);
                territory.setRegionId(regionId);
                
                territoryDAO.add(territory);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String description = request.getParameter("description");
                int regionId = Integer.parseInt(request.getParameter("regionId"));
                
                Territory territory = new Territory();
                territory.setId(id);
                territory.setDescription(description);
                territory.setRegionId(regionId);
                
                territoryDAO.update(territory);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                territoryDAO.delete(id);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        
        response.sendRedirect(request.getContextPath() + "/territories");
    }
} 