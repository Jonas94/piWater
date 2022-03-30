package com.example.piwater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class UserConfiguration {

    Map<String, Object> userSettings;

    @Autowired
    public UserConfiguration() {
        this.userSettings = new HashMap<>();
    }

    @Bean(name = "userSettings")
    public Map<String, Object> userSettings() {

        return userSettings;
    }
}
