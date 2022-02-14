package com.example.piwater.model;

public class Sensor {
    String name;
    Double value;
    String type;

    public Sensor(String name, Double value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
