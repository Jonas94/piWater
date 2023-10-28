package com.example.piwater.enums;

public enum MoistureSensors {
    SENSOR_1("Sensor 1"),
    SENSOR_2("Sensor 2");

    public final String sensorName;

    MoistureSensors(String sensorName) {
        this.sensorName = sensorName;
    }
}
