package com.example.dao;

import com.example.model.Employee;
import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс доступа к данным для сущности Employee
 */
public interface EmployeeDAO {
    /**
     * Получает сотрудника по его идентификатору
     *
     * @param id идентификатор сотрудника
     * @return объект сотрудника или null
     * @throws SQLException при ошибке доступа к базе данных
     */
    Employee getById(int id) throws SQLException;
    
    /**
     * Получает список всех сотрудников
     *
     * @return список всех сотрудников
     * @throws SQLException при ошибке доступа к базе данных
     */
    List<Employee> getAll() throws SQLException;
    
    /**
     * Добавляет нового сотрудника в базу данных
     * 
     * @param employee объект сотрудника для добавления
     * @return добавленный сотрудник с заполненным id
     * @throws SQLException при ошибке доступа к базе данных
     */
    Employee add(Employee employee) throws SQLException;
    
    /**
     * Обновляет информацию о сотруднике
     * 
     * @param employee объект сотрудника для обновления
     * @return true если обновление выполнено успешно
     * @throws SQLException при ошибке доступа к базе данных
     */
    boolean update(Employee employee) throws SQLException;
    
    /**
     * Удаляет сотрудника по его идентификатору
     *
     * @param id идентификатор сотрудника для удаления
     * @return true если удаление выполнено успешно
     * @throws SQLException при ошибке доступа к базе данных
     */
    boolean delete(int id) throws SQLException;
    
    /**
     * Получает список сотрудников по идентификатору территории
     *
     * @param territoryId идентификатор территории
     * @return список сотрудников для данной территории
     * @throws SQLException при ошибке доступа к базе данных
     */
    List<Employee> getByTerritoryId(int territoryId) throws SQLException;
} 