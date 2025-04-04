package com.example.servlet;

import com.example.util.DatabaseConnection;
import com.example.model.Region;

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

@WebServlet("/regions")
public class RegionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Region> regions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM region";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Region region = new Region(
                    rs.getInt("id"),
                    rs.getString("description")
                );
                regions.add(region);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        request.setAttribute("regions", regions);
        request.getRequestDispatcher("/WEB-INF/views/regions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection conn = DatabaseConnection.getConnection()) {
            if ("add".equals(action)) {
                String description = request.getParameter("description");
                String sql = "INSERT INTO region (description) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, description);
                stmt.executeUpdate();
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String description = request.getParameter("description");
                String sql = "UPDATE region SET description = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, description);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "DELETE FROM region WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        response.sendRedirect(request.getContextPath() + "/regions");
    }
} 