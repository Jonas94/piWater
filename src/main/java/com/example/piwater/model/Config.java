package com.example.piwater.model;

import lombok.Data;

@Data
public class Config {
    private boolean autoWateringEnabled;
    private int defaultWateringMinutes;
    private int moistureThreshold;

    public Config(boolean autoWateringEnabled, int defaultWateringMinutes, int moistureThreshold) {
        this.autoWateringEnabled = autoWateringEnabled;
        this.defaultWateringMinutes = defaultWateringMinutes;
        this.moistureThreshold = moistureThreshold;
    }

    public Config() {
    }

    public boolean isAutoWateringEnabled() {
        return autoWateringEnabled;
    }

    public int getDefaultWateringMinutes() {
        return defaultWateringMinutes;
    }

    public int getMoistureThreshold() {
        return moistureThreshold;
    }

    public void setAutoWateringEnabled(boolean autoWateringEnabled) {
        this.autoWateringEnabled = autoWateringEnabled;
    }

    public void setDefaultWateringMinutes(int defaultWateringMinutes) {
        this.defaultWateringMinutes = defaultWateringMinutes;
    }

    public void setMoistureThreshold(int moistureThreshold) {
        this.moistureThreshold = moistureThreshold;
    }
}
