package com.example.piwater.scheduling;

import com.example.piwater.mqtt.MQTTPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollSensors {
    private static final Logger log = LoggerFactory.getLogger(PollSensors.class);
    private final MQTTPublisher publisher;

    @Autowired
    public PollSensors(MQTTPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedRateString = "${sensor.check.time}")
    public void pollSensors() {
        log.info("Polling ESP32 to get current state of sensors");
        publisher.publishMessage("topic", "on"); //TODO: Replace with proper stuff
    }
}
