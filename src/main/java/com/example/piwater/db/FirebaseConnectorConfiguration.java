package com.example.piwater.db;

import com.example.piwater.model.Config;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Repository
public class FirebaseConnectorConfiguration extends FirebaseConnector {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConnectorConfiguration.class);

    public static final String CONFIGURATION = "configuration";
    public static final String CONFIG = "config";

    public static final String AUTO_WATERING_ENABLED = "autoWateringEnabled";
    public static final String DEFAULT_WATERING_MINUTES = "defaultWateringMinutes";
    public static final String MOISTURE_THRESHOLD = "moistureThreshold";


    public void addDataToFirestore(Config config) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(CONFIGURATION).document(CONFIG);

        Map<String, Object> data = new HashMap<>();
        data.put(DEFAULT_WATERING_MINUTES, config.getDefaultWateringMinutes());
        data.put(AUTO_WATERING_ENABLED, config.isAutoWateringEnabled());
        data.put(MOISTURE_THRESHOLD, config.getMoistureThreshold());
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

        ApiFutures.addCallback(result, new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("Data adding failed! {}", throwable.getMessage());
            }

            @Override
            public void onSuccess(WriteResult writeResult) {
                log.info("Data was added with success! {}", writeResult.toString());
            }

        }, MoreExecutors.directExecutor());
        log.info("Added moisture data to firestore! {}", data);
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

            return config;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
