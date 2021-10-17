package com.example.piwater.service;

import com.example.piwater.db.FirebaseConnector;
import com.example.piwater.utils.SensorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@ConditionalOnProperty(name = "raspberry.run", havingValue = "true", matchIfMissing = true)
public class TempServiceImpl implements TempService{

    FirebaseConnector firebaseConnector;
    SensorHelper sensorHelper;

    private static final Logger log = LoggerFactory.getLogger(TempServiceImpl.class);

    @Autowired
    public TempServiceImpl(FirebaseConnector firebaseConnector, SensorHelper sensorHelper) {
        this.firebaseConnector = firebaseConnector;
        this.sensorHelper = sensorHelper;
    }

    public double getCurrentTemperature() throws IOException {
        return sensorHelper.getTemperatureForSensor(sensorHelper.getSensorNames().get(0));
    }

    public List<String> getAllSensors() throws IOException {
        return sensorHelper.getSensorNames();
    }

}
