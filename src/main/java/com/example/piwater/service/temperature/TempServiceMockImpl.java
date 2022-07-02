package com.example.piwater.service.temperature;

import com.example.piwater.db.FirebaseConnectorTemperature;
import com.example.piwater.model.Temperature;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("local")
public class TempServiceMockImpl implements TempService {

    FirebaseConnectorTemperature firebaseConnector;
    private static final Logger log = LoggerFactory.getLogger(TempServiceMockImpl.class);

    @Autowired
    public TempServiceMockImpl(FirebaseConnectorTemperature firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    public Temperature getCurrentTemperature() {
        return new Temperature(99.0, LocalDateTime.now(), "Mocked sensor #1");
    }

    @Override
    public List<Temperature> getHistoricalTemperatures(LocalDateTime since) throws IOException {
        Temperature temp1 = new Temperature(55.0, LocalDateTime.now().minusDays(1), "Mocked sensor #1");
        List<Temperature> list = new ArrayList<>();
        list.add(temp1);
        return list;
    }

    public List<String> getAllSensors() throws IOException {
        return new ArrayList<>(List.of("mockedSensor1", "mockedSensor2"));
    }

    @Override
    public void saveCurrentTemp() throws IOException {
        TempInput tempInput = new TempInput();
        tempInput.setTemperature(-0.9);
        tempInput.setSensorId(getAllSensors().get(0));
        tempInput.setTimestamp(LocalDateTime.now());
        firebaseConnector.addDataToFirestore(tempInput);
    }

}
