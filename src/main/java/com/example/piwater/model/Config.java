package com.example.piwater.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class Config {
    private boolean autoWateringEnabled;
    private boolean enablePollingSensors;
    private int defaultWateringMinutes;
    private int moistureThreshold;
    private String sensorTopic;
    private int sensorCheckTime;

}
