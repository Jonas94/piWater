package com.example.piwater.model;

import java.time.LocalDateTime;

public class Temperature {
	private final Double temperatureInCelsius;
	private final LocalDateTime timestamp;
	private final String sensorId;

	public Temperature(Double temperatureInCelsius, LocalDateTime timestamp, String sensorId) {
		this.temperatureInCelsius = temperatureInCelsius;
		this.timestamp = timestamp;
		this.sensorId = sensorId;
	}

	public Double getTemperatureInCelsius() {
		return temperatureInCelsius;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getSensorId() {
		return sensorId;
	}
}
