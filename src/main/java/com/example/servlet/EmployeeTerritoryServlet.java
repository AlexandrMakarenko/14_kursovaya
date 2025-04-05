package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeTerritoryDAO;
import com.example.dao.TerritoryDAO;
import com.example.model.Employee;
import com.example.model.EmployeeTerritory;
import com.example.model.Territory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Сервлет для управления связями между сотрудниками и территориями
 */
@WebServlet("/employee-territories")
public class EmployeeTerritoryServlet extends HttpServlet {
    private EmployeeTerritoryDAO employeeTerritoryDAO;
    private EmployeeDAO employeeDAO;
    private TerritoryDAO territoryDAO;
    
    @Override
    public void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        employeeTerritoryDAO = daoFactory.getEmployeeTerritoryDAO();
        employeeDAO = daoFactory.getEmployeeDAO();
        territoryDAO = daoFactory.getTerritoryDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Получаем все записи связей между сотрудниками и территориями
            List<EmployeeTerritory> employeeTerritories = employeeTerritoryDAO.getAll();
            
            // Получаем списки сотрудников и территорий для выпадающих списков
            List<Employee> employees = employeeDAO.getAll();
            List<Territory> territories = territoryDAO.getAll();
            
            request.setAttribute("employeeTerritories", employeeTerritories);
            request.setAttribute("employees", employees);
            request.setAttribute("territories", territories);
            
            request.getRequestDispatcher("/WEB-INF/views/employee-territories.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении данных о назначениях территорий", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                addEmployeeTerritory(request);
            } else if ("delete".equals(action)) {
                deleteEmployeeTerritory(request);
            }
            
            response.sendRedirect(request.getContextPath() + "/employee-territories");
        } catch (Exception e) {
            throw new ServletException("Ошибка при выполнении операции с назначением территории", e);
        }
    }
    
    private void addEmployeeTerritory(HttpServletRequest request) throws SQLException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        int territoryId = Integer.parseInt(request.getParameter("territoryId"));
        
        // Проверяем, существует ли уже такая связь
        EmployeeTerritory existingET = employeeTerritoryDAO.getById(employeeId, territoryId);
        
        if (existingET == null) {
            EmployeeTerritory empTerritory = new EmployeeTerritory();
            empTerritory.setEmployeeId(employeeId);
            empTerritory.setTerritoryId(territoryId);
            
            employeeTerritoryDAO.add(empTerritory);
        }
    }
    
    private void deleteEmployeeTerritory(HttpServletRequest request) throws SQLException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        int territoryId = Integer.parseInt(request.getParameter("territoryId"));
        
        employeeTerritoryDAO.delete(employeeId, territoryId);
    }
} 