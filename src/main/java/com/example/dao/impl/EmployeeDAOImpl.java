package com.example.dao.impl;

import com.example.dao.EmployeeDAO;
import com.example.dao.DAOException;
import com.example.model.Employee;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация DAO для работы с таблицей Employee
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    
    private static final String SQL_SELECT_ALL = 
        "SELECT id, lastname, firstname, secondname, title, birthday, " +
        "address, city, region, phone, email FROM employee ORDER BY lastname, firstname";
    
    private static final String SQL_SELECT_BY_ID = 
        "SELECT id, lastname, firstname, secondname, title, birthday, " +
        "address, city, region, phone, email FROM employee WHERE id = ?";
    
    private static final String SQL_SELECT_BY_TERRITORY = 
        "SELECT e.id, e.lastname, e.firstname, e.secondname, e.title, e.birthday, " +
        "e.address, e.city, e.region, e.phone, e.email " +
        "FROM employee e " +
        "JOIN employeeterritory et ON e.id = et.employeeid " +
        "WHERE et.territoryid = ? " +
        "ORDER BY e.lastname, e.firstname";
    
    private static final String SQL_INSERT = 
        "INSERT INTO employee (lastname, firstname, secondname, title, birthday, " +
        "address, city, region, phone, email) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
    
    private static final String SQL_UPDATE = 
        "UPDATE employee SET lastname = ?, firstname = ?, secondname = ?, title = ?, " +
        "birthday = ?, address = ?, city = ?, region = ?, phone = ?, email = ? " +
        "WHERE id = ?";
    
    private static final String SQL_DELETE = "DELETE FROM employee WHERE id = ?";
    
    @Override
    public Employee getById(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении сотрудника по ID: " + id, e);
        }
    }
    
    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
            
            return employees;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка сотрудников", e);
        }
    }
    
    @Override
    public List<Employee> getByTerritoryId(int territoryId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_TERRITORY)) {
            
            stmt.setInt(1, territoryId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
                
                return employees;
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении сотрудников для территории с ID: " + territoryId, e);
        }
    }
    
    @Override
    public Employee add(Employee employee) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getSecondName());
            stmt.setString(4, employee.getTitle());
            if (employee.getBirthDay() != null) {
                stmt.setDate(5, new java.sql.Date(employee.getBirthDay().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            stmt.setString(6, employee.getAddress());
            stmt.setString(7, employee.getCity());
            stmt.setString(8, employee.getRegion());
            stmt.setString(9, employee.getPhone());
            stmt.setString(10, employee.getEmail());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    employee.setId(newId);
                    return employee;
                } else {
                    throw new DAOException("Не удалось получить ID для нового сотрудника");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении сотрудника", e);
        }
    }
    
    @Override
    public boolean update(Employee employee) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getSecondName());
            stmt.setString(4, employee.getTitle());
            if (employee.getBirthDay() != null) {
                stmt.setDate(5, new java.sql.Date(employee.getBirthDay().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            stmt.setString(6, employee.getAddress());
            stmt.setString(7, employee.getCity());
            stmt.setString(8, employee.getRegion());
            stmt.setString(9, employee.getPhone());
            stmt.setString(10, employee.getEmail());
            stmt.setInt(11, employee.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении сотрудника", e);
        }
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении сотрудника", e);
        }
    }
    
    /**
     * Преобразует результат запроса в объект Employee
     * 
     * @param rs результат запроса
     * @return объект Employee
     * @throws SQLException если произошла ошибка при чтении данных
     */
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        
        employee.setId(rs.getInt("id"));
        employee.setLastName(rs.getString("lastname"));
        employee.setFirstName(rs.getString("firstname"));
        employee.setSecondName(rs.getString("secondname"));
        employee.setTitle(rs.getString("title"));
        employee.setBirthDay(rs.getDate("birthday"));
        employee.setAddress(rs.getString("address"));
        employee.setCity(rs.getString("city"));
        employee.setRegion(rs.getString("region"));
        employee.setPhone(rs.getString("phone"));
        employee.setEmail(rs.getString("email"));
        
        return employee;
    }
} 