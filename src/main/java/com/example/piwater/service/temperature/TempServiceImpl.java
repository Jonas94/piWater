package com.example.piwater.service.temperature;

import com.example.piwater.db.FirebaseConnectorTemperature;
import com.example.piwater.model.Temperature;
import com.example.piwater.utils.SensorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@ConditionalOnProperty(name = "raspberry.run", havingValue = "true", matchIfMissing = true)
public class TempServiceImpl implements TempService {

    FirebaseConnectorTemperature firebaseConnector;
    SensorHelper sensorHelper;

    private static final Logger log = LoggerFactory.getLogger(TempServiceImpl.class);

    @Autowired
    public TempServiceImpl(FirebaseConnectorTemperature firebaseConnector, SensorHelper sensorHelper) {
        this.firebaseConnector = firebaseConnector;
        this.sensorHelper = sensorHelper;
    }

    public Temperature getCurrentTemperature() throws IOException {
        String sensorName = sensorHelper.getSensorNames().get(0);
        double currentTemp = sensorHelper.getTemperatureForSensor(sensorName);
        return new Temperature(currentTemp, LocalDateTime.now(), sensorName);
    }

    @Override
    public List<Temperature> getHistoricalTemperatures(LocalDateTime since) throws IOException {

        return new ArrayList<>();
    }

    public List<String> getAllSensors() throws IOException {
        return sensorHelper.getSensorNames();
    }

 //   @Scheduled(fixedRate = 900000)
    @Override
    public void saveCurrentTemp() throws IOException {
        Temperature temperature = getCurrentTemperature();
        TempInput tempInput = new TempInput();
        tempInput.setTemperature(temperature.getTemperatureInCelsius());
        tempInput.setSensorId(temperature.getSensorId());
        tempInput.setTimestamp(temperature.getTimestamp());
        firebaseConnector.addDataToFirestore(tempInput);
    }

}
