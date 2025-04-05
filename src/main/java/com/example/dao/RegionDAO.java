package com.example.dao;

import com.example.model.Region;
import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс доступа к данным для сущности Region
 */
public interface RegionDAO {
    
    /**
     * Получает регион по идентификатору
     * 
     * @param id идентификатор региона
     * @return объект региона или null, если регион не найден
     * @throws SQLException если произошла ошибка при работе с БД
     */
    Region getById(int id) throws SQLException;
    
    /**
     * Получает список всех регионов
     * 
     * @return список регионов
     * @throws SQLException если произошла ошибка при работе с БД
     */
    List<Region> getAll() throws SQLException;
    
    /**
     * Добавляет новый регион
     * 
     * @param region объект региона для добавления
     * @return добавленный регион с присвоенным идентификатором
     * @throws SQLException если произошла ошибка при работе с БД
     */
    Region add(Region region) throws SQLException;
    
    /**
     * Обновляет информацию о регионе
     * 
     * @param region объект региона для обновления
     * @return true, если обновление выполнено успешно
     * @throws SQLException если произошла ошибка при работе с БД
     */
    boolean update(Region region) throws SQLException;
    
    /**
     * Удаляет регион по идентификатору
     * 
     * @param id идентификатор региона для удаления
     * @return true, если удаление выполнено успешно
     * @throws SQLException если произошла ошибка при работе с БД
     */
    boolean delete(int id) throws SQLException;
} 