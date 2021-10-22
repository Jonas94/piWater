package com.example.piwater.service.temperature;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TempInput {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;
    private double temperature;
    private String sensorId;


    public TempInput(LocalDateTime timestamp, double temperature, String sensorId) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.sensorId = sensorId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public long getTimestampAsLong() {
        return timestamp.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli();
    }
}
