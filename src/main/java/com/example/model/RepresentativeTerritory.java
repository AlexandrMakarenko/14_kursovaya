package com.example.model;

public class RepresentativeTerritory {
    private int employeeId;
    private int territoryId;
    private Representative representative;
    private Territory territory;

    public RepresentativeTerritory() {}

    public RepresentativeTerritory(int employeeId, int territoryId) {
        this.employeeId = employeeId;
        this.territoryId = territoryId;
    }
    
    public RepresentativeTerritory(int employeeId, int territoryId, Representative representative, Territory territory) {
        this.employeeId = employeeId;
        this.territoryId = territoryId;
        this.representative = representative;
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
    
    public Representative getRepresentative() {
        return representative;
    }
    
    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
} 