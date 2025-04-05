package com.example.servlet;

import com.example.dao.DAOFactory;
import com.example.dao.EmployeeDAO;
import com.example.model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Сервлет для управления сотрудниками
 */
@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    
    @Override
    public void init() {
        employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Employee> employees = employeeDAO.getAll();
            request.setAttribute("employees", employees);
            request.getRequestDispatcher("/WEB-INF/views/employees.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении списка сотрудников", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                addEmployee(request);
            } else if ("update".equals(action)) {
                updateEmployee(request);
            } else if ("delete".equals(action)) {
                deleteEmployee(request);
            }
            
            response.sendRedirect(request.getContextPath() + "/employees");
        } catch (Exception e) {
            throw new ServletException("Ошибка при выполнении операции с сотрудником", e);
        }
    }
    
    private void addEmployee(HttpServletRequest request) throws SQLException, ParseException {
        Employee employee = new Employee();
        
        employee.setLastName(request.getParameter("lastName"));
        employee.setFirstName(request.getParameter("firstName"));
        employee.setSecondName(request.getParameter("secondName"));
        employee.setTitle(request.getParameter("title"));
        
        String birthDayStr = request.getParameter("birthDay");
        if (birthDayStr != null && !birthDayStr.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDay = sdf.parse(birthDayStr);
            employee.setBirthDay(birthDay);
        }
        
        employee.setAddress(request.getParameter("address"));
        employee.setCity(request.getParameter("city"));
        employee.setRegion(request.getParameter("region"));
        employee.setPhone(request.getParameter("phone"));
        employee.setEmail(request.getParameter("email"));
        
        employeeDAO.add(employee);
    }
    
    private void updateEmployee(HttpServletRequest request) throws SQLException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        Employee employee = employeeDAO.getById(id);
        if (employee != null) {
            employee.setLastName(request.getParameter("lastName"));
            employee.setFirstName(request.getParameter("firstName"));
            employee.setSecondName(request.getParameter("secondName"));
            employee.setTitle(request.getParameter("title"));
            
            String birthDayStr = request.getParameter("birthDay");
            if (birthDayStr != null && !birthDayStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDay = sdf.parse(birthDayStr);
                employee.setBirthDay(birthDay);
            }
            
            employee.setAddress(request.getParameter("address"));
            employee.setCity(request.getParameter("city"));
            employee.setRegion(request.getParameter("region"));
            employee.setPhone(request.getParameter("phone"));
            employee.setEmail(request.getParameter("email"));
            
            employeeDAO.update(employee);
        }
    }
    
    private void deleteEmployee(HttpServletRequest request) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        employeeDAO.delete(id);
    }
} 