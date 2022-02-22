package com.example.piwater.db;

import com.example.piwater.model.Moisture;
import com.example.piwater.model.Sensor;
import com.example.piwater.service.moisture.MoistureInput;
import com.example.piwater.service.watering.Watering;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;


@Repository
public class FirebaseConnectorMoisture extends FirebaseConnector {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConnectorMoisture.class);

    public static final String MOISTURE = "moisture";
    public static final String SENSOR = "sensor";

    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String TO = "to";

    public void addDataToFirestore(MoistureInput moistureInput) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(MOISTURE).document(moistureInput.getTimestamp().toString());

        Map<String, Object> data = new HashMap<>();
        data.put(TIME, moistureInput.getTimestamp());
        data.put(SENSOR, moistureInput.getSensorId());
        data.put(MOISTURE, moistureInput.getMoisture());
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

    public List<Moisture> findAllMoistureValues() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();
        //TODO: Implement correctly

        return findMoistureValuesWithQuery(db.collection(MOISTURE).orderBy(TIME, Query.Direction.DESCENDING).limit(10));
    }

    public List<Moisture> findMoistureValuesWithQuery(Query query) throws ExecutionException, InterruptedException {

        //TODO: Implement correctly
        List<Moisture> moistureValues = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            //TODO: Do something with this
        }
        return moistureValues;
    }

    public List<Moisture> findLatestMoistureValues(List<String> sensors) {
        List<Moisture> moistureValues = new ArrayList<>();

        Firestore db = getFirestore();
        CollectionReference moistureRecords = db.collection(MOISTURE);

        for (String sensorName : sensors) {
            try {

                Query query = moistureRecords.whereEqualTo(NAME, sensorName).orderBy(TIME, Query.Direction.DESCENDING).limit(1);
                ApiFuture<QuerySnapshot> querySnapshot = query.get();
                List<QueryDocumentSnapshot> documents = null;
                documents = querySnapshot.get().getDocuments();


                if (documents.size() == 1) {
                    Double moistureValue = -1.0;
                    String name = null;
                    LocalDateTime timestamp = LocalDateTime.now();

                    QueryDocumentSnapshot document = documents.get(0);

                    if (document.contains(MOISTURE)) {
                        moistureValue = document.getDouble(MOISTURE);
                    }
                    if (document.contains(NAME)) {
                        name = document.getString(NAME);
                    }
                    if (document.contains(TIME)) {
                        timestamp = Objects.requireNonNull(document.getTimestamp(TIME)).toSqlTimestamp().toLocalDateTime();
                    }

                    moistureValues.add(new Moisture(moistureValue, timestamp, name));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
        return moistureValues;

    }


}
