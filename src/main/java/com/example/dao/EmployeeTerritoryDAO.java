package com.example.dao;

import com.example.model.EmployeeTerritory;
import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс доступа к данным для сущности связи между сотрудниками и территориями
 */
public interface EmployeeTerritoryDAO {
    /**
     * Получает связь между сотрудником и территорией по идентификаторам
     *
     * @param employeeId идентификатор сотрудника
     * @param territoryId идентификатор территории
     * @return объект связи или null
     * @throws SQLException при ошибке доступа к базе данных
     */
    EmployeeTerritory getById(int employeeId, int territoryId) throws SQLException;
    
    /**
     * Получает список всех связей между сотрудниками и территориями
     *
     * @return список всех связей
     * @throws SQLException при ошибке доступа к базе данных
     */
    List<EmployeeTerritory> getAll() throws SQLException;
    
    /**
     * Добавляет новую связь между сотрудником и территорией
     *
     * @param employeeTerritory объект связи для добавления
     * @return добавленная связь
     * @throws SQLException при ошибке доступа к базе данных
     */
    EmployeeTerritory add(EmployeeTerritory employeeTerritory) throws SQLException;
    
    /**
     * Удаляет связь между сотрудником и территорией
     *
     * @param employeeId идентификатор сотрудника
     * @param territoryId идентификатор территории
     * @return true если удаление выполнено успешно
     * @throws SQLException при ошибке доступа к базе данных
     */
    boolean delete(int employeeId, int territoryId) throws SQLException;
    
    /**
     * Получает список связей по идентификатору сотрудника
     *
     * @param employeeId идентификатор сотрудника
     * @return список связей для данного сотрудника
     * @throws SQLException при ошибке доступа к базе данных
     */
    List<EmployeeTerritory> getByEmployeeId(int employeeId) throws SQLException;
    
    /**
     * Получает список связей по идентификатору территории
     *
     * @param territoryId идентификатор территории
     * @return список связей для данной территории
     * @throws SQLException при ошибке доступа к базе данных
     */
    List<EmployeeTerritory> getByTerritoryId(int territoryId) throws SQLException;
} 