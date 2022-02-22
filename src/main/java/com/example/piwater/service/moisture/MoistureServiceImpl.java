package com.example.piwater.service.moisture;

import com.example.piwater.db.FirebaseConnectorMoisture;
import com.example.piwater.model.MessageModel;
import com.example.piwater.model.Moisture;
import com.example.piwater.model.Sensor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

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
        return List.of(new Moisture(0.0, null, "1")); //TODO: REPLACE WITH CORRECT STUFF
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
