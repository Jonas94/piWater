package com.example.piwater.waterbrain;

import com.example.piwater.exception.IsBusyException;
import com.example.piwater.model.Moisture;
import com.example.piwater.model.Sensor;
import com.example.piwater.service.moisture.MoistureService;
import com.example.piwater.service.watering.WaterInput;
import com.example.piwater.service.watering.WaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Brain {

    WaterService waterService;
    MoistureService moistureService;

    @Value("${watering.threshold.value}")
    private double thresholdValue;
    @Value("${watering.minutes.to.water}")
    private int minutesToWater;

    private static final Logger log = LoggerFactory.getLogger(Brain.class);

    @Autowired
    public Brain(WaterService waterService, MoistureService moistureService) {
        this.waterService = waterService;
        this.moistureService = moistureService;
    }

    public void handleMoistureInformationAndTakeAction(List<Sensor> sensors) throws IOException {
        double totalMoistureValue = 0;

        for (Sensor sensor : sensors) {
            moistureService.saveCurrentMoistureValue(new Moisture(sensor.getValue(), LocalDateTime.now(), sensor.getName()));
            totalMoistureValue += sensor.getValue();
        }

        if (totalMoistureValue / sensors.size() < thresholdValue) {
            try {
                log.info("Starting a watering since value was below threshold!");
                waterService.enableWateringForDuration(new WaterInput(minutesToWater));
            } catch (IsBusyException e) {
                log.info("Water system is busy, please wait for next check");
            }
        }
    }
}
