package com.example.piwater.userconfig;

import com.example.piwater.constants.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserConfiguration {

    @Value("${watering.default.threshold.value}")
    private int defaultThresholdValue;

    @Value("${watering.default.minutes.to.water}")
    private int defaultMinutesToWater;

    @Value("${messaging.outbound.sensor.topic}")
    private String defaultSensorTopic;

    @Value("${watering.default.sensor.polling}")
    private boolean defaultSensorPolling;

    @Value("${sensor.check.time}")
    private int defaultSensorCheckTime;

    Map<String, Object> userSettings;

    public UserConfiguration() {
        this.userSettings = new HashMap<>();
    }

    @Bean(name = "userSettings")
    public Map<String, Object> userSettings() {
        userSettings.put(Keys.AUTO_WATERING_ENABLED, false); //TODO: Put it in the props file
        userSettings.put(Keys.DEFAULT_WATERING_MINUTES, defaultMinutesToWater);
        userSettings.put(Keys.MOISTURE_THRESHOLD, defaultThresholdValue);
        userSettings.put(Keys.SENSOR_TOPIC, defaultSensorTopic);
        userSettings.put(Keys.ENABLE_POLLING_SENSORS, defaultSensorPolling);
        userSettings.put(Keys.SENSOR_CHECK_TIME, defaultSensorCheckTime);

        return userSettings;
    }
}
