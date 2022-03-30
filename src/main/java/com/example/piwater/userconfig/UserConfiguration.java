package com.example.piwater.userconfig;

import com.example.piwater.constants.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class UserConfiguration {

    @Value("${watering.default.threshold.value}")
    private double defaultThresholdValue;
    @Value("${watering.default.minutes.to.water}")
    private int defaultMinutesToWater;

    Map<String, Object> userSettings;

    @Autowired
    public UserConfiguration() {
        this.userSettings = new HashMap<>();
    }

    @Bean(name = "userSettings")
    public Map<String, Object> userSettings() {

        userSettings.put(Keys.AUTO_WATERING_ENABLED, false); //TODO: Put it in the props file
        userSettings.put(Keys.DEFAULT_WATERING_MINUTES, defaultMinutesToWater);
        userSettings.put(Keys.MOISTURE_THRESHOLD, defaultThresholdValue);


        return userSettings;
    }
}
