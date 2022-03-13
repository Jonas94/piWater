package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.enums.MoistureSensors;
import com.example.piwater.model.MessageModel;
import com.example.piwater.model.Moisture;
import com.example.piwater.waterbrain.Brain;
import com.google.gson.Gson;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
//@ConditionalOnProperty(name = "raspberry.run", havingValue = "true", matchIfMissing = true)
public class MoistureServiceImpl implements MoistureService {

    FirebaseConnectorMoisture firebaseConnector;

    private static final Logger log = LoggerFactory.getLogger(MoistureServiceImpl.class);


    @Autowired
    public MoistureServiceImpl(FirebaseConnectorMoisture firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

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
    public void saveCurrentMoistureValue(Moisture moisture) throws IOException {
        MoistureInput moistureInput = new MoistureInput();
        moistureInput.setMoisture(moisture.getMoistureValue());
        moistureInput.setSensorId(moisture.getSensorId());
        moistureInput.setTimestamp(moisture.getTimestamp());
        firebaseConnector.addDataToFirestore(moistureInput);
    }
}
