package com.example.dao.impl;

import com.example.dao.RegionDAO;
import com.example.dao.DAOException;
import com.example.model.Region;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация DAO для работы с таблицей Region
 */
public class RegionDAOImpl implements RegionDAO {
    
    private static final String SQL_SELECT_ALL = "SELECT id, description FROM region ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, description FROM region WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO region (description) VALUES (?) RETURNING id";
    private static final String SQL_UPDATE = "UPDATE region SET description = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM region WHERE id = ?";
    
    @Override
    public Region getById(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRegion(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении региона по ID: " + id, e);
        }
    }
    
    @Override
    public List<Region> getAll() throws SQLException {
        List<Region> regions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                regions.add(mapResultSetToRegion(rs));
            }
            
            return regions;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка регионов", e);
        }
    }
    
    @Override
    public Region add(Region region) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, region.getDescription());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    region.setId(id);
                    return region;
                } else {
                    throw new DAOException("Не удалось получить ID для нового региона");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении региона", e);
        }
    }
    
    @Override
    public boolean update(Region region) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, region.getDescription());
            stmt.setInt(2, region.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении региона", e);
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
            throw new DAOException("Ошибка при удалении региона", e);
        }
    }
    
    /**
     * Преобразует результат запроса в объект Region
     * 
     * @param rs результат запроса
     * @return объект Region
     * @throws SQLException если произошла ошибка при чтении данных
     */
    private Region mapResultSetToRegion(ResultSet rs) throws SQLException {
        Region region = new Region();
        region.setId(rs.getInt("id"));
        region.setDescription(rs.getString("description"));
        return region;
    }
} 