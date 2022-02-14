package com.example.piwater.waterbrain;

import com.example.piwater.exception.IsBusyException;
import com.example.piwater.model.Moisture;
import com.example.piwater.model.Sensor;
import com.example.piwater.scheduling.PollFirestore;
import com.example.piwater.service.watering.WaterInput;
import com.example.piwater.service.watering.WaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Brain {

    WaterService waterService;
    private static final Logger log = LoggerFactory.getLogger(Brain.class);

    @Autowired
    public Brain(WaterService waterService) {
        this.waterService = waterService;
    }

    public void handleMoistureInformationAndTakeAction(List<Sensor> sensors) {

        //TODO: Handle multiple sensors and do something like averages etc.


        //TODO: Handle default minutesToWater from properties, maybe from firestore?
        //TODO: Handle default threshold value from properties, maybe from firestore?

        if (sensors.get(0).getValue() < 50) {
            try {
                log.info("******************");
                log.info("Starting a watering since value was below threshold!");
                log.info("******************");

                waterService.enableWateringForDuration(new WaterInput(5));
            } catch (IsBusyException e) {
                log.info("Water system is busy, please wait for next check");
            }
        }


    }

}
