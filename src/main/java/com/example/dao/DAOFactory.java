package com.example.dao;

import com.example.dao.impl.RegionDAOImpl;
import com.example.dao.impl.TerritoryDAOImpl;
import com.example.dao.impl.EmployeeDAOImpl;
import com.example.dao.impl.EmployeeTerritoryDAOImpl;

/**
 * Фабрика для создания объектов DAO
 */
public class DAOFactory {
    private static DAOFactory instance;
    
    private final RegionDAO regionDAO;
    private final TerritoryDAO territoryDAO;
    private final EmployeeDAO employeeDAO;
    private final EmployeeTerritoryDAO employeeTerritoryDAO;
    
    private DAOFactory() {
        regionDAO = new RegionDAOImpl();
        territoryDAO = new TerritoryDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
        employeeTerritoryDAO = new EmployeeTerritoryDAOImpl();
    }
    
    /**
     * Получает единственный экземпляр фабрики
     * 
     * @return экземпляр фабрики
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
    
    /**
     * Получает DAO для работы с регионами
     * 
     * @return объект RegionDAO
     */
    public RegionDAO getRegionDAO() {
        return regionDAO;
    }
    
    /**
     * Получает DAO для работы с территориями
     * 
     * @return объект TerritoryDAO
     */
    public TerritoryDAO getTerritoryDAO() {
        return territoryDAO;
    }
    
    /**
     * Получает DAO для работы с сотрудниками
     * 
     * @return объект EmployeeDAO
     */
    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }
    
    /**
     * Получает DAO для работы с назначениями территорий сотрудникам
     * 
     * @return объект EmployeeTerritoryDAO
     */
    public EmployeeTerritoryDAO getEmployeeTerritoryDAO() {
        return employeeTerritoryDAO;
    }
} 