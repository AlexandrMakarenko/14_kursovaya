package com.example.dao.impl;

import com.example.dao.TerritoryDAO;
import com.example.dao.DAOException;
import com.example.model.Territory;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация DAO для работы с таблицей Territory
 */
public class TerritoryDAOImpl implements TerritoryDAO {
    
    private static final String SQL_SELECT_ALL = "SELECT id, description, regionId FROM territory ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, description, regionId FROM territory WHERE id = ?";
    private static final String SQL_SELECT_BY_REGION_ID = "SELECT id, description, regionId FROM territory WHERE regionId = ? ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO territory (description, regionId) VALUES (?, ?) RETURNING id";
    private static final String SQL_UPDATE = "UPDATE territory SET description = ?, regionId = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM territory WHERE id = ?";
    
    @Override
    public Territory getById(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTerritory(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении территории по ID: " + id, e);
        }
    }
    
    @Override
    public List<Territory> getAll() throws SQLException {
        List<Territory> territories = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                territories.add(mapResultSetToTerritory(rs));
            }
            
            return territories;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка территорий", e);
        }
    }
    
    @Override
    public List<Territory> getByRegionId(int regionId) throws SQLException {
        List<Territory> territories = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_REGION_ID)) {
            
            stmt.setInt(1, regionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    territories.add(mapResultSetToTerritory(rs));
                }
                
                return territories;
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении территорий для региона с ID: " + regionId, e);
        }
    }
    
    @Override
    public Territory add(Territory territory) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, territory.getDescription());
            stmt.setInt(2, territory.getRegionId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    territory.setId(id);
                    return territory;
                } else {
                    throw new DAOException("Не удалось получить ID для новой территории");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении территории", e);
        }
    }
    
    @Override
    public boolean update(Territory territory) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, territory.getDescription());
            stmt.setInt(2, territory.getRegionId());
            stmt.setInt(3, territory.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении территории", e);
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
            throw new DAOException("Ошибка при удалении территории", e);
        }
    }
    
    /**
     * Преобразует результат запроса в объект Territory
     * 
     * @param rs результат запроса
     * @return объект Territory
     * @throws SQLException если произошла ошибка при чтении данных
     */
    private Territory mapResultSetToTerritory(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String description = rs.getString("description");
        int regionId = rs.getInt("regionId");
        
        Territory territory = new Territory();
        territory.setId(id);
        territory.setDescription(description);
        territory.setRegionId(regionId);
        
        return territory;
    }
} 