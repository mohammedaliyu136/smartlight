package com.mohammedaliyu.smartswitchlite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "light_table")
public class Light {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type;
    private Boolean status;
    private String light_id;

    public Light(String name, String type, Boolean status, String light_id) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.light_id = light_id;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getLight_id() {
        return light_id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLight_id(String light_id) {
        this.light_id = light_id;
    }
}
