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
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 * Сервлет для получения данных о сотруднике для редактирования в модальном окне
 */
@WebServlet("/employee-edit")
public class EmployeeEditServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    
    @Override
    public void init() {
        employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            Employee employee = employeeDAO.getById(employeeId);
            
            if (employee != null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("id", employee.getId());
                jsonResponse.put("lastName", employee.getLastName());
                jsonResponse.put("firstName", employee.getFirstName());
                jsonResponse.put("secondName", employee.getSecondName());
                jsonResponse.put("title", employee.getTitle());
                
                if (employee.getBirthDay() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    jsonResponse.put("birthDay", sdf.format(employee.getBirthDay()));
                } else {
                    jsonResponse.put("birthDay", "");
                }
                
                jsonResponse.put("address", employee.getAddress());
                jsonResponse.put("city", employee.getCity());
                jsonResponse.put("region", employee.getRegion());
                jsonResponse.put("phone", employee.getPhone());
                jsonResponse.put("email", employee.getEmail());
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Сотрудник не найден");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Ошибка при получении данных сотрудника: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Некорректный ID сотрудника");
        }
    }
} 