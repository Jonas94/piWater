package com.example.piwater.service.temperature;

import com.example.piwater.db.FirebaseConnectorTemperature;
import com.example.piwater.model.Temperature;
import com.example.piwater.utils.SensorHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Profile("!local")
public class TempServiceImpl implements TempService {

    private final FirebaseConnectorTemperature firebaseConnector;
    private final SensorHelper sensorHelper;

    private static final Logger log = LoggerFactory.getLogger(TempServiceImpl.class);

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
        tempInput.setTemperature(temperature.temperatureInCelsius());
        tempInput.setSensorId(temperature.sensorId());
        tempInput.setTimestamp(temperature.timestamp());
        firebaseConnector.addDataToFirestore(tempInput);
    }

}
