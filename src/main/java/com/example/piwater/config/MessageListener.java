package com.example.piwater.config;


import com.example.piwater.mqtt.MQTTSubscriberBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageListener implements Runnable {

    @Autowired
    MQTTSubscriberBase subscriber;

    @Override
    public void run() {
        while (true) {
            subscriber.subscribeMessage("topic");
        }

    }

}