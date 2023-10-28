package com.example.piwater.service.moisture;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class MoistureInput {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;
    private double moisture;
    private String sensorId;

    private static final ZoneId zoneId = ZoneId.of("Europe/Paris");

    public MoistureInput() {
    }

    public MoistureInput(LocalDateTime timestamp, double moisture, String sensorId) {
        this.timestamp = timestamp;
        this.moisture = moisture;
        this.sensorId = sensorId;
    }

    public long getDateAsLong() {
        return timestamp.atZone(zoneId).toInstant().toEpochMilli();
    }

    public void setDateAsLong(Long epochMillis) {
        Instant instant = Instant.ofEpochMilli(epochMillis);
        timestamp = instant.atZone(zoneId).toLocalDateTime();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public long getTimestampAsLong() {
        return timestamp.atZone(zoneId).toInstant().toEpochMilli();
    }
}
