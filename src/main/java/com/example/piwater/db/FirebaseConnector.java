package com.example.piwater.db;

import com.example.piwater.service.*;
import com.google.api.core.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Repository
public class FirebaseConnector {

    public static final String START_TIME = "startTime";
    public static final String STOP_TIME = "stopTime";
    public static final String DURATION = "duration";
    public static final String WATERING = "watering";
    public static final String FROM = "from";
    public static final String TO = "to";

    private static final Logger log = LoggerFactory.getLogger(FirebaseConnector.class);


    public static void initializeFireStore() throws IOException {
        ClassPathResource resource = new ClassPathResource("waterbutler-7b556-firebase-adminsdk-zgbd1-01123dd482.json");
        InputStream inputStream = resource.getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }

    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public void addDataToFirestore(WaterInput waterInput) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(WATERING).document(waterInput.getStartDate().toString());

        Map<String, Object> data = new HashMap<>();
        data.put(START_TIME, waterInput.getStartDateAsLong());
        data.put(STOP_TIME, waterInput.getStopDateAsLong());

        data.put(DURATION, waterInput.getMinutesToWater());
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

        log.info("Added watering to firestore!");
    }

    public void updateRecurringWateringToFirestore(RecurringWatering recurringWatering) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection("recurringWatering").document(recurringWatering.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("day", recurringWatering.getDays());
        data.put("time", recurringWatering.getTime());
        data.put(DURATION, recurringWatering.getDuration());
        data.put("active", recurringWatering.isActive());

        ApiFuture<WriteResult> result = docRef.set(data, SetOptions.merge());


        log.info("Data written to firebase!");
    }

    public List<RecurringWatering> findAllRecurringWaterings() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();

        CollectionReference query = db.collection("recurringWatering");
        List<RecurringWatering> recurringWaterings = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            RecurringWatering recurringWatering = new RecurringWatering();


            recurringWatering.setId(document.getId());

            if (document.contains("active")) {
                recurringWatering.setActive(document.getBoolean("active"));
            }
            if (document.contains("day")) {
                recurringWatering.setDays((List<String>) document.get("day"));
            }
            if (document.contains("time")) {
                recurringWatering.setTime((String) document.get("time"));
            }


            if (document.contains(DURATION)) {
                recurringWatering.setDuration(Integer.parseInt(String.valueOf(document.get(DURATION))));
            }

            recurringWaterings.add(recurringWatering);
        }
        return recurringWaterings;
    }

    public List<RecurringWatering> findActiveRecurringWaterings() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();

        CollectionReference recurringWateringCollection = db.collection("recurringWatering");

        Date date = new Date();
        long now = date.getTime();
        Query query = recurringWateringCollection.whereEqualTo("active", true);

        List<RecurringWatering> recurringWaterings = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            RecurringWatering recurringWatering = new RecurringWatering();
            if (document.contains("active")) {
                recurringWatering.setActive(document.getBoolean("active"));
            }
            if (document.contains("day")) {
                recurringWatering.setDays((List<String>) document.get("day"));
            }
            if (document.contains("time")) {
                recurringWatering.setTime((String) document.get("time"));
            }

            if (document.contains(DURATION)) {
                recurringWatering.setDuration(Integer.parseInt(String.valueOf(document.get(DURATION))));
            }


            recurringWaterings.add(recurringWatering);
        }
        return recurringWaterings;
    }


    public List<Watering> findAllWaterings() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();

        return findWateringsWithQuery(db.collection(WATERING).orderBy(START_TIME, Query.Direction.DESCENDING).limit(10));
    }

    public List<Watering> findPossiblyOngoingWaterings() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();


        CollectionReference waterings = db.collection(WATERING);
        Query query = waterings.whereGreaterThanOrEqualTo(STOP_TIME, new Date().getTime());

        return findWateringsWithQuery(query);
    }

    public List<Watering> findFutureWaterings() throws ExecutionException, InterruptedException {
        Firestore db = getFirestore();

        CollectionReference waterings = db.collection(WATERING);
        Query query = waterings.whereGreaterThanOrEqualTo(START_TIME, new Date().getTime());

        return findWateringsWithQuery(query);
    }

    public List<Watering> findWateringsWithQuery(Query query) throws ExecutionException, InterruptedException {

        List<Watering> waterings = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Watering watering = new Watering();
            if (document.contains(START_TIME)) {
                watering.setStartDate(document.getLong(START_TIME));
            }
            if (document.contains(STOP_TIME)) {
                watering.setStopDate(document.getLong(STOP_TIME));
            }
            if (document.contains(DURATION)) {
                watering.setDuration(Math.toIntExact(document.getLong(DURATION)));
            }
            waterings.add(watering);
        }
        return waterings;
    }


}
