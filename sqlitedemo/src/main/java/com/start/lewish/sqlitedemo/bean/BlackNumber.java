package com.start.lewish.sqlitedemo.bean;

public class BlackNumber {
    private int id;
    private String number;

    public BlackNumber() {
        super();
    }

    public BlackNumber(int id, String number) {
        super();
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BlackNumber [id=" + id + ", number=" + number + "]";
    }

}
