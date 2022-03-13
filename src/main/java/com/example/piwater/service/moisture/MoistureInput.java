package com.example.piwater.service.moisture;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MoistureInput {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;
    private double moisture;
    private String sensorId;

    public MoistureInput() {
    }

    public MoistureInput(LocalDateTime timestamp, double moisture, String sensorId) {
        this.timestamp = timestamp;
        this.moisture = moisture;
        this.sensorId = sensorId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getDateAsLong() {
        return timestamp.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli();
    }

    public void setDateAsLong(Long epochMillis) {
        Instant instant = Instant.ofEpochMilli(epochMillis);
        timestamp = instant.atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
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
