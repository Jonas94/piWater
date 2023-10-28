package com.example.piwater.model;

import java.time.LocalDateTime;

public record Moisture(Double moistureValue, LocalDateTime timestamp, String sensorId) {

}
