package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.enums.MoistureSensors;
import com.example.piwater.model.Moisture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Primary
@Service
public class MoistureServiceImpl implements MoistureService {

    private final FirebaseConnectorMoisture firebaseConnector;

    private static final Logger log = LoggerFactory.getLogger(MoistureServiceImpl.class);

    public List<Moisture> getCurrentMoistureValues() {
        List<String> sensorNames = new ArrayList<>();
        for (MoistureSensors moistureSensor : MoistureSensors.values()) {
            sensorNames.add(moistureSensor.name);
        }
        return firebaseConnector.findLatestMoistureValues(sensorNames);
    }

    @Override
    public List<Moisture> getHistoricalMoistureValues(LocalDateTime since) {
        return null; //TODO: Implement this
    }

    @Override
    public void saveCurrentMoistureValue(Moisture moisture) {
        MoistureInput moistureInput = new MoistureInput();
        moistureInput.setMoisture(moisture.getMoistureValue());
        moistureInput.setSensorId(moisture.getSensorId());
        moistureInput.setTimestamp(moisture.getTimestamp());
        firebaseConnector.addDataToFirestore(moistureInput);
    }
}
