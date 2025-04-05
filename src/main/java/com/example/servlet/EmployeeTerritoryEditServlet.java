package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.EmployeeTerritoryDAO;
import com.example.model.EmployeeTerritory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 * Сервлет для получения данных о связи сотрудник-территория для редактирования в модальном окне
 */
@WebServlet("/employeeterritory-edit")
public class EmployeeTerritoryEditServlet extends HttpServlet {
    private EmployeeTerritoryDAO employeeTerritoryDAO;
    
    @Override
    public void init() {
        employeeTerritoryDAO = DAOFactory.getInstance().getEmployeeTerritoryDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            int territoryId = Integer.parseInt(request.getParameter("territoryId"));
            
            EmployeeTerritory employeeTerritory = employeeTerritoryDAO.getById(employeeId, territoryId);
            
            if (employeeTerritory != null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("employeeId", employeeTerritory.getEmployeeId());
                jsonResponse.put("territoryId", employeeTerritory.getTerritoryId());
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Связь сотрудник-территория не найдена");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Ошибка при получении данных связи: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Некорректные параметры запроса");
        }
    }
} 