package com.example.piwater.scheduling;

import com.example.piwater.constants.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

public class PollSensorsTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(PollSensorsTask.class);

    private Map<String, Object> userSettings;

    private JmsTemplate jmsTemplate;

    public PollSensorsTask(Map<String, Object> userSettings, JmsTemplate jmsTemplate) {
        this.userSettings = userSettings;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run() {
        if ((boolean) userSettings.getOrDefault(Keys.ENABLE_POLLING_SENSORS, false)) {
            String sensorTopic = (String) userSettings.getOrDefault(Keys.SENSOR_TOPIC, "topic1");
            log.info("Polling ESP32 to get current state of sensors on topic: {}", sensorTopic);
            jmsTemplate.send(sensorTopic, session -> session.createTextMessage("on"));
        }
    }
}


