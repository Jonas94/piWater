package com.example.piwater.db;

import com.example.piwater.service.temperature.TempInput;
import com.example.piwater.service.watering.RecurringWatering;
import com.example.piwater.service.watering.WaterInput;
import com.example.piwater.service.watering.Watering;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


@Repository
public class FirebaseConnectorTemperature extends FirebaseConnector {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConnectorTemperature.class);

    public static final String TEMPERATURE = "temperature";
    public static final String SENSOR = "sensor";

    public static final String TIME = "time";
    public static final String TO = "to";
    public static final String RECURRING_WATERING = "recurringWatering";

    public void addDataToFirestore(TempInput tempInput) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(TEMPERATURE).document(tempInput.getTimestamp().toString());

        Map<String, Object> data = new HashMap<>();
        data.put(TIME, tempInput.getTimestamp());
        data.put(SENSOR, tempInput.getSensorId());
        data.put(TEMPERATURE, tempInput.getTemperature());
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

        log.info("Added temperature data to firestore! {}", data);
    }

    public List<Watering> findAllTemperatures() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore(); //TODO: Fix this method, it will break.

        return findWateringsWithQuery(db.collection(TEMPERATURE).orderBy(TIME, Query.Direction.DESCENDING).limit(10));
    }

    public List<Watering> findWateringsWithQuery(Query query) throws ExecutionException, InterruptedException {

        List<Watering> waterings = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            //TODO: Do something with this
        }
        return waterings;
    }


}
