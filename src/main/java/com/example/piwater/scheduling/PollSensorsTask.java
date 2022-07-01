package com.example.piwater.scheduling;

import com.example.piwater.constants.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;


@Deprecated
public class PollSensorsTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(PollSensorsTask.class);

    private JmsTemplate jmsTemplate;

    public PollSensorsTask(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run() {
        //tring sensorTopic = (String) userSettings.getOrDefault(Keys.SENSOR_TOPIC, "topic1");
        String sensorTopic = "topic";
        log.info("Polling ESP32 to get current state of sensors on topic: {}", sensorTopic);
        jmsTemplate.send(sensorTopic, session -> session.createTextMessage("on"));
    }
}


