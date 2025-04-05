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
import java.util.List;

@WebServlet("/regions")
public class RegionsServlet extends HttpServlet {
    private RegionDAO regionDAO;
    
    @Override
    public void init() {
        regionDAO = DAOFactory.getInstance().getRegionDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Region> regions = regionDAO.getAll();
            request.setAttribute("regions", regions);
            request.getRequestDispatcher("/WEB-INF/views/regions.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении списка регионов", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                String description = request.getParameter("description");
                Region region = new Region();
                region.setDescription(description);
                regionDAO.add(region);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String description = request.getParameter("description");
                Region region = new Region(id, description);
                regionDAO.update(region);
            }
            response.sendRedirect(request.getContextPath() + "/regions");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Ошибка при работе с регионами", e);
        }
    }
} 