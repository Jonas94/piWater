package com.example.piwater.model;

import java.util.List;

public class MessageModel {
    List<Sensor> sensors;


    public MessageModel(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
