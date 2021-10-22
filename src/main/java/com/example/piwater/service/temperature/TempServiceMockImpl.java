package com.example.piwater.service.temperature;

import com.example.piwater.db.FirebaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@ConditionalOnProperty(name = "raspberry.run", havingValue = "false")
public class TempServiceMockImpl implements TempService{

    FirebaseConnector firebaseConnector;
    private static final Logger log = LoggerFactory.getLogger(TempServiceMockImpl.class);

    @Autowired
    public TempServiceMockImpl(FirebaseConnector firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    public double getCurrentTemperature() {
        return 99;
    }

    public List<String> getAllSensors() throws IOException {
        return new ArrayList<>(List.of("mockedSensor1", "mockedSensor2"));
    }

}
