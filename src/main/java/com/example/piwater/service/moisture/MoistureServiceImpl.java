package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.enums.MoistureSensors;
import com.example.piwater.model.Moisture;
import lombok.RequiredArgsConstructor;
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

    public List<Moisture> getCurrentMoistureValues() {
        List<String> sensorNames = new ArrayList<>();
        for (MoistureSensors moistureSensor : MoistureSensors.values()) {
            sensorNames.add(moistureSensor.sensorName);
        }
        return firebaseConnector.findLatestMoistureValues(sensorNames);
    }

    @Override
    public List<Moisture> getHistoricalMoistureValues(LocalDateTime since) {
        throw new UnsupportedOperationException("Not yet implemented");
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
