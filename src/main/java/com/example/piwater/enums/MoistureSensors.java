package com.example.piwater.enums;

public enum MoistureSensors {
    SENSOR_1("Sensor 1"),
    SENSOR_2("Sensor 2");

    public final String name;

    private MoistureSensors(String name) {
        this.name = name;
    }
}
