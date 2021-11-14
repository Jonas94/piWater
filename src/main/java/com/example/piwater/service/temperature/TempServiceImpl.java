package com.example.piwater.service.temperature;

import com.example.piwater.db.FirebaseConnector;
import com.example.piwater.db.FirebaseConnectorTemperature;
import com.example.piwater.utils.SensorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "raspberry.run", havingValue = "true", matchIfMissing = true)
public class TempServiceImpl implements TempService{

    FirebaseConnectorTemperature firebaseConnector;
    SensorHelper sensorHelper;

    private static final Logger log = LoggerFactory.getLogger(TempServiceImpl.class);

    @Autowired
    public TempServiceImpl(FirebaseConnectorTemperature firebaseConnector, SensorHelper sensorHelper) {
        this.firebaseConnector = firebaseConnector;
        this.sensorHelper = sensorHelper;
    }

    public double getCurrentTemperature() throws IOException {
        return sensorHelper.getTemperatureForSensor(sensorHelper.getSensorNames().get(0));
    }

    public List<String> getAllSensors() throws IOException {
        return sensorHelper.getSensorNames();
    }

    @Scheduled(fixedRate = 900000)
    @Override
    public void saveCurrentTemp() throws IOException {
        TempInput tempInput = new TempInput();
        tempInput.setTemperature(getCurrentTemperature());
        tempInput.setSensorId(getAllSensors().get(0));
        tempInput.setTimestamp(LocalDateTime.now());
        firebaseConnector.addDataToFirestore(tempInput);
    }

}
