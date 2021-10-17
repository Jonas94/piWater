package com.example.piwater.service;

import com.example.piwater.db.FirebaseConnector;
import com.example.piwater.utils.SensorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TempService {

    FirebaseConnector firebaseConnector;
    private static final Logger log = LoggerFactory.getLogger(TempService.class);

    @Autowired
    public TempService(FirebaseConnector firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    public double getCurrentTemperature() throws IOException {
        //SensorHelper.getSensorNames();
        return 99; //TODO: Fetch from device or firestore
    }

    public List<String> getAllSensors() throws IOException {
        return SensorHelper.getSensorNames();

    }

}
