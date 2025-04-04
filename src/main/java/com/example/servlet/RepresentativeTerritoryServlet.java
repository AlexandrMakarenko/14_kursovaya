package com.example.servlet;

import com.example.util.DatabaseConnection;
import com.example.model.Representative;
import com.example.model.RepresentativeTerritory;
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

@WebServlet("/representative-territories")
public class RepresentativeTerritoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RepresentativeTerritory> representativeTerritories = new ArrayList<>();
        List<Representative> representatives = new ArrayList<>();
        List<Territory> territories = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Получение списка связей
            String sql = "SELECT rt.EmployeeId, rt.TerritoryId, " + 
                        "r.LastName, r.FirstName, r.SecondName, r.Title, " +
                        "t.description as territoryDescription " +
                        "FROM employeeterritory rt " +
                        "JOIN employee r ON rt.EmployeeId = r.id " +
                        "JOIN territory t ON rt.TerritoryId = t.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Representative representative = new Representative();
                representative.setId(rs.getInt("EmployeeId"));
                representative.setLastName(rs.getString("LastName"));
                representative.setFirstName(rs.getString("FirstName"));
                representative.setSecondName(rs.getString("SecondName"));
                representative.setTitle(rs.getString("Title"));
                
                Territory territory = new Territory();
                territory.setId(rs.getInt("TerritoryId"));
                territory.setDescription(rs.getString("territoryDescription"));
                
                RepresentativeTerritory rt = new RepresentativeTerritory(
                    rs.getInt("EmployeeId"),
                    rs.getInt("TerritoryId"),
                    representative,
                    territory
                );
                
                representativeTerritories.add(rt);
            }
            
            // Получение списка представителей
            sql = "SELECT id, LastName, FirstName, SecondName, Title FROM employee";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Representative representative = new Representative();
                representative.setId(rs.getInt("id"));
                representative.setLastName(rs.getString("LastName"));
                representative.setFirstName(rs.getString("FirstName"));
                representative.setSecondName(rs.getString("SecondName"));
                representative.setTitle(rs.getString("Title"));
                
                representatives.add(representative);
            }
            
            // Получение списка территорий
            sql = "SELECT t.id, t.description, t.regionId, r.description as regionDescription " +
                  "FROM territory t JOIN region r ON t.regionId = r.id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Territory territory = new Territory(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getInt("regionId"),
                    rs.getString("regionDescription")
                );
                
                territories.add(territory);
            }
            
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        
        request.setAttribute("representativeTerritories", representativeTerritories);
        request.setAttribute("representatives", representatives);
        request.setAttribute("territories", territories);
        request.getRequestDispatcher("/WEB-INF/views/representative-territories.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if ("add".equals(action)) {
                int employeeId = Integer.parseInt(request.getParameter("employeeId"));
                int territoryId = Integer.parseInt(request.getParameter("territoryId"));
                
                // Проверка на существование связи
                String checkSql = "SELECT COUNT(*) FROM employeeterritory WHERE EmployeeId = ? AND TerritoryId = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, employeeId);
                checkStmt.setInt(2, territoryId);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next() && rs.getInt(1) == 0) {
                    String sql = "INSERT INTO employeeterritory (EmployeeId, TerritoryId) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, employeeId);
                    stmt.setInt(2, territoryId);
                    stmt.executeUpdate();
                }
            } else if ("delete".equals(action)) {
                int employeeId = Integer.parseInt(request.getParameter("employeeId"));
                int territoryId = Integer.parseInt(request.getParameter("territoryId"));
                
                String sql = "DELETE FROM employeeterritory WHERE EmployeeId = ? AND TerritoryId = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, employeeId);
                stmt.setInt(2, territoryId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        
        response.sendRedirect(request.getContextPath() + "/representative-territories");
    }
} 