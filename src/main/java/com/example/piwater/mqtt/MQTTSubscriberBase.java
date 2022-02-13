package com.example.piwater.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MQTTSubscriberBase {

    public static final Logger logger = LoggerFactory.getLogger(MQTTPublisherBase.class);

    public void subscribeMessage(String topic);

    /**
     * Disconnect MQTT Client
     */
    public void disconnect();
}
