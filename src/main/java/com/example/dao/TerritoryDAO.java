package com.example.dao;

import com.example.model.Territory;
import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс доступа к данным для сущности Territory
 */
public interface TerritoryDAO {
    
    /**
     * Получает территорию по идентификатору
     * 
     * @param id идентификатор территории
     * @return объект территории или null, если территория не найдена
     * @throws SQLException если произошла ошибка при работе с БД
     */
    Territory getById(int id) throws SQLException;
    
    /**
     * Получает список всех территорий
     * 
     * @return список территорий
     * @throws SQLException если произошла ошибка при работе с БД
     */
    List<Territory> getAll() throws SQLException;
    
    /**
     * Получает список территорий, принадлежащих указанному региону
     * 
     * @param regionId идентификатор региона
     * @return список территорий
     * @throws SQLException если произошла ошибка при работе с БД
     */
    List<Territory> getByRegionId(int regionId) throws SQLException;
    
    /**
     * Добавляет новую территорию
     * 
     * @param territory объект территории для добавления
     * @return добавленная территория
     * @throws SQLException если произошла ошибка при работе с БД
     */
    Territory add(Territory territory) throws SQLException;
    
    /**
     * Обновляет информацию о территории
     * 
     * @param territory объект территории для обновления
     * @return true, если обновление выполнено успешно
     * @throws SQLException если произошла ошибка при работе с БД
     */
    boolean update(Territory territory) throws SQLException;
    
    /**
     * Удаляет территорию по идентификатору
     * 
     * @param id идентификатор территории для удаления
     * @return true, если удаление выполнено успешно
     * @throws SQLException если произошла ошибка при работе с БД
     */
    boolean delete(int id) throws SQLException;
} 