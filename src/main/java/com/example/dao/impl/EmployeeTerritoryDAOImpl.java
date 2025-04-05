package com.example.dao.impl;

import com.example.dao.EmployeeTerritoryDAO;
import com.example.dao.DAOException;
import com.example.model.EmployeeTerritory;
import com.example.model.Employee;
import com.example.model.Territory;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация DAO для работы с таблицей сотрудник-территория
 */
public class EmployeeTerritoryDAOImpl implements EmployeeTerritoryDAO {

    private static final String SQL_SELECT_ALL = 
        "SELECT et.employeeid, et.territoryid, " +
        "e.id, e.lastname, e.firstname, e.secondname, e.title, e.birthday, e.address, e.city, " +
        "e.region AS empregion, e.phone, e.email, " +
        "t.description AS territorydescription, t.regionid AS territoryregionid " +
        "FROM employeeterritory et " +
        "JOIN employee e ON et.employeeid = e.id " +
        "JOIN territory t ON et.territoryid = t.id " +
        "ORDER BY e.lastname, e.firstname, t.description";
    
    private static final String SQL_SELECT_BY_ID = 
        "SELECT et.employeeid, et.territoryid, " +
        "e.id, e.lastname, e.firstname, e.secondname, e.title, e.birthday, e.address, e.city, " +
        "e.region AS empregion, e.phone, e.email, " +
        "t.description AS territorydescription, t.regionid AS territoryregionid " +
        "FROM employeeterritory et " +
        "JOIN employee e ON et.employeeid = e.id " +
        "JOIN territory t ON et.territoryid = t.id " +
        "WHERE et.employeeid = ? AND et.territoryid = ?";
        
    private static final String SQL_SELECT_BY_EMPLOYEE_ID = 
        "SELECT et.employeeid, et.territoryid, " +
        "e.id, e.lastname, e.firstname, e.secondname, e.title, e.birthday, e.address, e.city, " +
        "e.region AS empregion, e.phone, e.email, " +
        "t.description AS territorydescription, t.regionid AS territoryregionid " +
        "FROM employeeterritory et " +
        "JOIN employee e ON et.employeeid = e.id " +
        "JOIN territory t ON et.territoryid = t.id " +
        "WHERE et.employeeid = ? " +
        "ORDER BY t.description";
        
    private static final String SQL_SELECT_BY_TERRITORY_ID = 
        "SELECT et.employeeid, et.territoryid, " +
        "e.id, e.lastname, e.firstname, e.secondname, e.title, e.birthday, e.address, e.city, " +
        "e.region AS empregion, e.phone, e.email, " +
        "t.description AS territorydescription, t.regionid AS territoryregionid " +
        "FROM employeeterritory et " +
        "JOIN employee e ON et.employeeid = e.id " +
        "JOIN territory t ON et.territoryid = t.id " +
        "WHERE et.territoryid = ? " +
        "ORDER BY e.lastname, e.firstname";
    
    private static final String SQL_INSERT = 
        "INSERT INTO employeeterritory (employeeid, territoryid) VALUES (?, ?)";
    
    private static final String SQL_DELETE = 
        "DELETE FROM employeeterritory WHERE employeeid = ? AND territoryid = ?";
    
    @Override
    public EmployeeTerritory getById(int employeeId, int territoryId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, employeeId);
            stmt.setInt(2, territoryId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployeeTerritory(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении назначения по ID сотрудника и территории", e);
        }
    }
    
    @Override
    public List<EmployeeTerritory> getAll() throws SQLException {
        List<EmployeeTerritory> assignments = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                assignments.add(mapResultSetToEmployeeTerritory(rs));
            }
            
            return assignments;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка всех назначений", e);
        }
    }
    
    @Override
    public List<EmployeeTerritory> getByEmployeeId(int employeeId) throws SQLException {
        List<EmployeeTerritory> assignments = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_EMPLOYEE_ID)) {
            
            stmt.setInt(1, employeeId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assignments.add(mapResultSetToEmployeeTerritory(rs));
                }
                
                return assignments;
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении назначений для сотрудника с ID: " + employeeId, e);
        }
    }
    
    @Override
    public List<EmployeeTerritory> getByTerritoryId(int territoryId) throws SQLException {
        List<EmployeeTerritory> assignments = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_TERRITORY_ID)) {
            
            stmt.setInt(1, territoryId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assignments.add(mapResultSetToEmployeeTerritory(rs));
                }
                
                return assignments;
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении назначений для территории с ID: " + territoryId, e);
        }
    }
    
    @Override
    public EmployeeTerritory add(EmployeeTerritory employeeTerritory) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, employeeTerritory.getEmployeeId());
            stmt.setInt(2, employeeTerritory.getTerritoryId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return employeeTerritory;
            } else {
                throw new DAOException("Ошибка при добавлении назначения");
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении назначения", e);
        }
    }
    
    @Override
    public boolean delete(int employeeId, int territoryId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, employeeId);
            stmt.setInt(2, territoryId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении назначения", e);
        }
    }
    
    /**
     * Преобразует результат запроса в объект EmployeeTerritory
     * 
     * @param rs результат запроса
     * @return объект EmployeeTerritory
     * @throws SQLException если произошла ошибка при чтении данных
     */
    private EmployeeTerritory mapResultSetToEmployeeTerritory(ResultSet rs) throws SQLException {
        EmployeeTerritory assignment = new EmployeeTerritory();
        assignment.setEmployeeId(rs.getInt("employeeid"));
        assignment.setTerritoryId(rs.getInt("territoryid"));
        
        // Создаем объект Employee
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setLastName(rs.getString("lastname"));
        employee.setFirstName(rs.getString("firstname"));
        employee.setSecondName(rs.getString("secondname"));
        employee.setTitle(rs.getString("title"));
        employee.setBirthDay(rs.getDate("birthday"));
        employee.setAddress(rs.getString("address"));
        employee.setCity(rs.getString("city"));
        employee.setRegion(rs.getString("empregion"));
        employee.setPhone(rs.getString("phone"));
        employee.setEmail(rs.getString("email"));
        
        // Создаем объект Territory
        Territory territory = new Territory();
        territory.setId(rs.getInt("territoryid"));
        territory.setDescription(rs.getString("territorydescription"));
        territory.setRegionId(rs.getInt("territoryregionid"));
        
        // Устанавливаем связи
        assignment.setEmployee(employee);
        assignment.setTerritory(territory);
        
        return assignment;
    }
} 