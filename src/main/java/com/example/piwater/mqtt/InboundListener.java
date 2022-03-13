package com.example.piwater.mqtt;

import com.example.piwater.model.MessageModel;
import com.example.piwater.waterbrain.Brain;
import com.google.gson.Gson;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class InboundListener {

    private Brain brain;

    @Autowired
    public InboundListener(Brain brain) {
        this.brain = brain;
    }

    @JmsListener(destination = "${messaging.inbound.sensor.topic}")
    public void handleIncomingMessage(ActiveMQBytesMessage string) throws IOException {
        String s = new String(string.getContent().getData(), StandardCharsets.UTF_8);

        System.out.println("MESSAGE RECEIVED:" + s);

        Gson gson = new Gson();
        MessageModel messageModel = gson.fromJson(s, MessageModel.class);
        System.out.println(messageModel.getSensors().get(0));

        brain.handleMoistureInformationAndTakeAction(messageModel.getSensors());
    }
}
