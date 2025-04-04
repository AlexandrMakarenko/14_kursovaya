package com.example.model;

public class Territory {
    private int id;
    private int regionId;
    private String description;
    private String regionDescription;

    public Territory() {}

    public Territory(int id, int regionId, String description) {
        this.id = id;
        this.regionId = regionId;
        this.description = description;
    }
    
    public Territory(int id, String description, int regionId, String regionDescription) {
        this.id = id;
        this.description = description;
        this.regionId = regionId;
        this.regionDescription = regionDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegionDescription() {
        return regionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }
} 