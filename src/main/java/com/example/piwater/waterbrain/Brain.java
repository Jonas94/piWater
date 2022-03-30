package com.example.piwater.waterbrain;

import com.example.piwater.constants.Keys;
import com.example.piwater.exception.IsBusyException;
import com.example.piwater.model.Moisture;
import com.example.piwater.model.Sensor;
import com.example.piwater.service.moisture.MoistureService;
import com.example.piwater.service.watering.WaterInput;
import com.example.piwater.service.watering.WaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class Brain {

    WaterService waterService;
    MoistureService moistureService;

    @Resource(name = "userSettings")
    private Map<String, Object> userSettings;

    private static final Logger log = LoggerFactory.getLogger(Brain.class);

    @Autowired
    public Brain(WaterService waterService, MoistureService moistureService) {
        this.waterService = waterService;
        this.moistureService = moistureService;
    }

    public void handleMoistureInformationAndTakeAction(List<Sensor> sensors) throws IOException {
        double totalMoistureValue = 0;

        int thresholdValue = (int) userSettings.getOrDefault(Keys.MOISTURE_THRESHOLD, 20);
        int minutesToWater = (int) userSettings.getOrDefault(Keys.DEFAULT_WATERING_MINUTES, 1);
        boolean autoWateringEnabled = (boolean) userSettings.getOrDefault(Keys.AUTO_WATERING_ENABLED, false);

        LocalDateTime now = LocalDateTime.now();
        for (Sensor sensor : sensors) {
            moistureService.saveCurrentMoistureValue(new Moisture(sensor.getValue(), now, sensor.getName()));
            totalMoistureValue += sensor.getValue();
        }

        if (totalMoistureValue / sensors.size() < thresholdValue && autoWateringEnabled) {
            try {
                log.info("Starting a watering since value was below threshold!");
                waterService.enableWateringForDuration(new WaterInput(minutesToWater));
            } catch (IsBusyException e) {
                log.info("Water system is busy, please wait for next check");
            }
        }
    }
}
