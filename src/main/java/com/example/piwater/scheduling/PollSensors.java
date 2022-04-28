package com.example.piwater.scheduling;

import com.example.piwater.constants.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;

@Component
public class PollSensors {
    private static final Logger log = LoggerFactory.getLogger(PollSensors.class);
    private final JmsTemplate jmsTemplate;

    @Resource(name = "userSettings")
    private Map<String, Object> userSettings;

    @Autowired
    public PollSensors(JmsTemplate publisher) {
        this.jmsTemplate = publisher;
    }

    @Scheduled(fixedRateString = "${sensor.check.time}")
    public void pollSensors() {
        if ((boolean) userSettings.getOrDefault(Keys.ENABLE_POLLING_SENSORS, false)) {
            String sensorTopic = (String) userSettings.getOrDefault(Keys.SENSOR_TOPIC, "topic1");
            log.info("Polling ESP32 to get current state of sensors on topic: {}", sensorTopic);
            jmsTemplate.send(sensorTopic, session -> session.createTextMessage("on"));
        }
    }
}
