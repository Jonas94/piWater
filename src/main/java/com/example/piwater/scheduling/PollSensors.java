package com.example.piwater.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollSensors {
    private static final Logger log = LoggerFactory.getLogger(PollSensors.class);
    private final JmsTemplate jmsTemplate;

    @Value("${messaging.outbound.sensor.topic}")
    private String sensorTopic;

    @Value("${sensors.enable.polling}")
    private boolean enablePollingSensors;

    @Autowired
    public PollSensors(JmsTemplate publisher) {
        this.jmsTemplate = publisher;
    }

    @Scheduled(fixedRateString = "${sensor.check.time}")
    public void pollSensors() {
        if(enablePollingSensors) {
            log.info("Polling ESP32 to get current state of sensors on topic: {}", sensorTopic);
            jmsTemplate.send(sensorTopic, session -> session.createTextMessage("on"));
        }
    }
}
