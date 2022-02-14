package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.model.Moisture;
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
public class MoistureServiceImpl implements MoistureService {

    FirebaseConnectorMoisture firebaseConnector;

    private static final Logger log = LoggerFactory.getLogger(MoistureServiceImpl.class);

    @Autowired
    public MoistureServiceImpl(FirebaseConnectorMoisture firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    public List<Moisture> getCurrentMoistureValues() {
        return List.of(new Moisture(0.0, null, "1")); //TODO: REPLACE WITH CORRECT STUFF
    }

    @Override
    public List<Moisture> getHistoricalMoistureValues(LocalDateTime since) {
        return null; //TODO: Implement this
    }

    @Scheduled(fixedRate = 900000)
    @Override
    public void saveCurrentMoistureValue() throws IOException {
        Moisture moisture = getCurrentMoistureValues().get(0); //TODO: Fix this whenever there is time, allow lists
        MoistureInput moistureInput = new MoistureInput();
        moistureInput.setMoisture(moisture.getMoistureValue());
        moistureInput.setSensorId(moisture.getSensorId());
        moistureInput.setTimestamp(moisture.getTimestamp());
        firebaseConnector.addDataToFirestore(moistureInput);
    }

}
