package com.example.hayk.gametime.objects;

/**
 * Created by Hayk on 27.07.2017.
 */

public class DatabaseObject {
    private Integer ID;
    private String Data;
    private boolean Used;

    public DatabaseObject(Integer ID, String Data){
        this.ID = ID;
        this.Data = Data;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public boolean isUsed() {
        return Used;
    }

    public void setUsed(boolean used) {
        Used = used;
    }
}
