package com.example.model;

public class EmployeeTerritory {
    private int employeeId;
    private int territoryId;
    private Employee employee;
    private Territory territory;

    public EmployeeTerritory() {}

    public EmployeeTerritory(int employeeId, int territoryId) {
        this.employeeId = employeeId;
        this.territoryId = territoryId;
    }
    
    public EmployeeTerritory(int employeeId, int territoryId, Employee employee, Territory territory) {
        this.employeeId = employeeId;
        this.territoryId = territoryId;
        this.employee = employee;
        this.territory = territory;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(int territoryId) {
        this.territoryId = territoryId;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
}