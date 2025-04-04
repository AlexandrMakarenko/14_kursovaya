package com.example.servlet;

import com.example.util.DatabaseConnection;
import com.example.model.Representative;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/representatives")
public class RepresentativeServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Representative> representatives = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM employee";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Representative representative = new Representative(
                    rs.getInt("id"),
                    rs.getString("LastName"),
                    rs.getString("FirstName"),
                    rs.getString("SecondName"),
                    rs.getString("Title"),
                    rs.getDate("BirthDay"),
                    rs.getString("Address"),
                    rs.getString("City"),
                    rs.getString("Region"),
                    rs.getString("Phone"),
                    rs.getString("Email")
                );
                representatives.add(representative);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        request.setAttribute("representatives", representatives);
        request.getRequestDispatcher("/WEB-INF/views/representatives.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection conn = DatabaseConnection.getConnection()) {
            if ("add".equals(action)) {
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String secondName = request.getParameter("secondName");
                String title = request.getParameter("title");
                String birthDayStr = request.getParameter("birthDay");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String region = request.getParameter("region");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                
                String sql = "INSERT INTO employee (LastName, FirstName, SecondName, Title, BirthDay, Address, City, Region, Phone, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, lastName);
                stmt.setString(2, firstName);
                stmt.setString(3, secondName);
                stmt.setString(4, title);
                
                if (birthDayStr != null && !birthDayStr.isEmpty()) {
                    try {
                        Date birthDay = DATE_FORMAT.parse(birthDayStr);
                        stmt.setDate(5, new java.sql.Date(birthDay.getTime()));
                    } catch (ParseException e) {
                        stmt.setNull(5, java.sql.Types.DATE);
                    }
                } else {
                    stmt.setNull(5, java.sql.Types.DATE);
                }
                
                stmt.setString(6, address);
                stmt.setString(7, city);
                stmt.setString(8, region);
                stmt.setString(9, phone);
                stmt.setString(10, email);
                stmt.executeUpdate();
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String secondName = request.getParameter("secondName");
                String title = request.getParameter("title");
                String birthDayStr = request.getParameter("birthDay");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String region = request.getParameter("region");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                
                String sql = "UPDATE employee SET LastName = ?, FirstName = ?, SecondName = ?, Title = ?, BirthDay = ?, Address = ?, City = ?, Region = ?, Phone = ?, Email = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, lastName);
                stmt.setString(2, firstName);
                stmt.setString(3, secondName);
                stmt.setString(4, title);
                
                if (birthDayStr != null && !birthDayStr.isEmpty()) {
                    try {
                        Date birthDay = DATE_FORMAT.parse(birthDayStr);
                        stmt.setDate(5, new java.sql.Date(birthDay.getTime()));
                    } catch (ParseException e) {
                        stmt.setNull(5, java.sql.Types.DATE);
                    }
                } else {
                    stmt.setNull(5, java.sql.Types.DATE);
                }
                
                stmt.setString(6, address);
                stmt.setString(7, city);
                stmt.setString(8, region);
                stmt.setString(9, phone);
                stmt.setString(10, email);
                stmt.setInt(11, id);
                stmt.executeUpdate();
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "DELETE FROM employee WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        response.sendRedirect(request.getContextPath() + "/representatives");
    }
} 