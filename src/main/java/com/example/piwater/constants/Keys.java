package com.example.piwater.constants;

public final class Keys {

    private Keys() throws IllegalAccessException {
        throw new IllegalAccessException("Should never be instanced");
    }

    public static final String AUTO_WATERING_ENABLED = "autoWateringEnabled";
    public static final String DEFAULT_WATERING_MINUTES = "defaultWateringMinutes";
    public static final String MOISTURE_THRESHOLD = "moistureThreshold";
    public static final String SENSOR_TOPIC = "sensorTopic";
    public static final String ENABLE_POLLING_SENSORS = "enablePollingSensors";
    public static final String SENSOR_CHECK_TIME = "sensorCheckTime";

}
