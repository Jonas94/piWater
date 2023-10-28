package com.example.piwater.db;

import com.example.piwater.model.Config;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Repository
public class FirebaseConnectorConfiguration extends FirebaseConnector {

    public static final String CONFIGURATION = "configuration";
    public static final String CONFIG = "config";

    public static final String AUTO_WATERING_ENABLED = "autoWateringEnabled";
    public static final String DEFAULT_WATERING_MINUTES = "defaultWateringMinutes";
    public static final String MOISTURE_THRESHOLD = "moistureThreshold";
    public static final String SENSOR_TOPIC = "sensorTopic";
    public static final String ENABLE_POLLING_SENSORS = "enablePollingSensors";


    public ApiFuture<WriteResult> addDataToFirestore(Config config) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(CONFIGURATION).document(CONFIG);

        //TODO: Pass config as map instead and put it all in there
        Map<String, Object> data = new HashMap<>();
        data.put(DEFAULT_WATERING_MINUTES, config.getDefaultWateringMinutes());
        data.put(AUTO_WATERING_ENABLED, config.isAutoWateringEnabled());
        data.put(MOISTURE_THRESHOLD, config.getMoistureThreshold());
        data.put(SENSOR_TOPIC, config.getSensorTopic());
        data.put(ENABLE_POLLING_SENSORS, config.isEnablePollingSensors());
        //asynchronously write data

        return docRef.set(data);
    }


    public Config findCurrentConfig() {
        Firestore db = getFirestore();
        CollectionReference collection = db.collection(CONFIGURATION);

        try {
            ApiFuture<QuerySnapshot> querySnapshot = collection.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            QueryDocumentSnapshot document = documents.get(0);

            Config config = new Config();

            if (document.contains(AUTO_WATERING_ENABLED)) {
                config.setAutoWateringEnabled(Boolean.TRUE.equals(document.getBoolean(AUTO_WATERING_ENABLED)));
            }
            if (document.contains(DEFAULT_WATERING_MINUTES)) {
                config.setDefaultWateringMinutes(Integer.parseInt(String.valueOf(document.get(DEFAULT_WATERING_MINUTES))));
            }

            if (document.contains(MOISTURE_THRESHOLD)) {
                config.setMoistureThreshold(Integer.parseInt(String.valueOf(document.get(MOISTURE_THRESHOLD))));
            }

            if (document.contains(SENSOR_TOPIC)) {
                config.setSensorTopic((String) document.get(SENSOR_TOPIC));
            }

            if (document.contains(ENABLE_POLLING_SENSORS)) {
                config.setEnablePollingSensors(Boolean.TRUE.equals(document.getBoolean(ENABLE_POLLING_SENSORS)));
            }

            return config;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
