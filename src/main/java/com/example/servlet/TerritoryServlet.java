package com.example.servlet;

import com.example.util.DatabaseConnection;
import com.example.model.Region;
import com.example.model.Territory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/territories")
public class TerritoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Territory> territories = new ArrayList<>();
        List<Region> regions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Получение списка регионов
            String regionSql = "SELECT * FROM region";
            PreparedStatement regStmt = conn.prepareStatement(regionSql);
            ResultSet regRs = regStmt.executeQuery();
            while (regRs.next()) {
                Region region = new Region(
                    regRs.getInt("id"),
                    regRs.getString("description")
                );
                regions.add(region);
            }
            
            // Получение списка территорий с названиями регионов
            String territorySql = "SELECT t.id, t.description, t.regionId, r.description as regionDescription " +
                                  "FROM territory t " +
                                  "JOIN region r ON t.regionId = r.id";
            PreparedStatement terrStmt = conn.prepareStatement(territorySql);
            ResultSet terrRs = terrStmt.executeQuery();
            while (terrRs.next()) {
                Territory territory = new Territory(
                    terrRs.getInt("id"),
                    terrRs.getString("description"),
                    terrRs.getInt("regionId"),
                    terrRs.getString("regionDescription")
                );
                territories.add(territory);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        
        request.setAttribute("territories", territories);
        request.setAttribute("regions", regions);
        request.getRequestDispatcher("/WEB-INF/views/territories.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection conn = DatabaseConnection.getConnection()) {
            if ("add".equals(action)) {
                String description = request.getParameter("description");
                int regionId = Integer.parseInt(request.getParameter("regionId"));
                
                String sql = "INSERT INTO territory (description, regionId) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, description);
                stmt.setInt(2, regionId);
                stmt.executeUpdate();
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String description = request.getParameter("description");
                int regionId = Integer.parseInt(request.getParameter("regionId"));
                
                String sql = "UPDATE territory SET description = ?, regionId = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, description);
                stmt.setInt(2, regionId);
                stmt.setInt(3, id);
                stmt.executeUpdate();
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                
                String sql = "DELETE FROM territory WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        response.sendRedirect(request.getContextPath() + "/territories");
    }
} 