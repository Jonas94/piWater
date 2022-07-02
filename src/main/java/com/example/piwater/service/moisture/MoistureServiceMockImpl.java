package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.model.Moisture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
@Profile("local")
public class MoistureServiceMockImpl implements MoistureService {

    FirebaseConnectorMoisture firebaseConnector;
    private static final Logger log = LoggerFactory.getLogger(MoistureServiceMockImpl.class);

    @Override
    public List<Moisture> getCurrentMoistureValues() throws IOException {
        return List.of(new Moisture(99.0, LocalDateTime.now(), "Mocked sensor #1"));
    }

    @Override
    public List<Moisture> getHistoricalMoistureValues(LocalDateTime since) throws IOException {
        return List.of(new Moisture(99.0, LocalDateTime.now(), "Mocked sensor #1"));
    }

    @Override
    public void saveCurrentMoistureValue(Moisture moisture) throws IOException {
        MoistureInput moistureInput = new MoistureInput();
        moistureInput.setMoisture(54.5);
        moistureInput.setSensorId("Mocked sensor #2");
        moistureInput.setTimestamp(LocalDateTime.now());
        //  firebaseConnector.addDataToFirestore(moistureInput);
    }

}
