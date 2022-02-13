package com.example.piwater.mqtt;

public interface MQTTPublisherBase {

    public void publishMessage(String topic, String message);

    /**
     * Disconnect MQTT Client
     */
    public void disconnect();

}