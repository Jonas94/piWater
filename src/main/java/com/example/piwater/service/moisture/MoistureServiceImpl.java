package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.enums.MoistureSensors;
import com.example.piwater.model.MessageModel;
import com.example.piwater.model.Moisture;
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
        return firebaseConnector.findLatestMoistureValues(Stream.of(MoistureSensors.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
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

    @JmsListener(destination = "${messaging.inbound.sensor.topic}")
    public void handleIncomingMessage(ActiveMQBytesMessage string) {
        String s = new String(string.getContent().getData(), StandardCharsets.UTF_8);

        System.out.println("MESSAGE RECEIVED:" + s);

        Gson gson = new Gson();
        MessageModel messageModel = gson.fromJson(s, MessageModel.class);
        System.out.println(messageModel.getSensors().get(0));

    }
}
