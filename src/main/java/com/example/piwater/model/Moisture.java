package com.example.piwater.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class Moisture {

    private final Double moistureValue;
    private final LocalDateTime timestamp;
    private final String sensorId;
}
