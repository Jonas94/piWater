package com.example.piwater.mqtt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InboundListener {
/*
    private final Brain brain;

    @JmsListener(destination = "${messaging.inbound.sensor.topic}")
    public void handleIncomingMessage(ActiveMQBytesMessage string) throws IOException {
        String s = new String(string.getContent().getData(), StandardCharsets.UTF_8);

        System.out.println("MESSAGE RECEIVED:" + s);

        Gson gson = new Gson();
        MessageModel messageModel = gson.fromJson(s, MessageModel.class);

        brain.handleMoistureInformationAndTakeAction(messageModel.getSensors());
    }*/
}
