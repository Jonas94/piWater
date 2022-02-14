package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.db.FirebaseConnectorTemperature;
import com.example.piwater.model.Moisture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
@ConditionalOnProperty(name = "raspberry.run", havingValue = "false")
public class MoistureServiceMockImpl implements MoistureService {

    FirebaseConnectorMoisture firebaseConnector;
    private static final Logger log = LoggerFactory.getLogger(MoistureServiceMockImpl.class);

    @Autowired
    public MoistureServiceMockImpl(FirebaseConnectorMoisture firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    @Override
    public List<Moisture> getCurrentMoistureValues() throws IOException {
        return List.of(new Moisture(99.0, LocalDateTime.now(), "Mocked sensor #1"));
    }

    @Override
    public List<Moisture> getHistoricalMoistureValues(LocalDateTime since) throws IOException {
        return List.of(new Moisture(99.0, LocalDateTime.now(), "Mocked sensor #1"));
    }

    @Override
    public void saveCurrentMoistureValue() throws IOException {
        MoistureInput moistureInput = new MoistureInput();
        moistureInput.setMoisture(54.5);
        moistureInput.setSensorId("Mocked sensor #2");
        moistureInput.setTimestamp(LocalDateTime.now());
        firebaseConnector.addDataToFirestore(moistureInput);
    }

}
