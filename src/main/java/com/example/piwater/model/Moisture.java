package com.example.piwater.model;

import java.time.LocalDateTime;

public class Moisture {
    private final Double moistureValue;
    private final LocalDateTime timestamp;
    private final String sensorId;

    public Moisture(Double moistureValue, LocalDateTime timestamp, String sensorId) {
        this.moistureValue = moistureValue;
        this.timestamp = timestamp;
        this.sensorId = sensorId;
    }

    public Double getMoistureValue() {
        return moistureValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSensorId() {
        return sensorId;
    }
}
