package com.example.piwater.model;

import java.time.LocalDateTime;

public record Temperature(Double temperatureInCelsius, LocalDateTime timestamp, String sensorId) {

}
