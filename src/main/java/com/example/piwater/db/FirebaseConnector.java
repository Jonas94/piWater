package com.example.piwater.db;
import com.example.piwater.scheduling.*;
import com.example.piwater.service.*;
import com.google.api.core.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.*;
import org.slf4j.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Repository
public class FirebaseConnector
{
	public static final String START_TIME = "startTime";
	public static final String DURATION = "duration";


	public static void initializeFireStore() throws IOException {

		ClassPathResource resource = new ClassPathResource("waterbutler-7b556-cac4931051f1.json");
		InputStream inputStream = resource.getInputStream();
		GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.build();
		FirebaseApp.initializeApp(options);
	}

	public Firestore getFirestore() {

		Firestore db = FirestoreClient.getFirestore();


		return db;

	}


	public void addDataToFirestore(WaterInput waterInput) throws ExecutionException, InterruptedException {
		Firestore db = getFirestore();
		DocumentReference docRef = db.collection("watering").document(waterInput.getStartDate().toString());

		Map<String, Object> data = new HashMap<>();
		data.put(START_TIME, waterInput.getStartDateAsLong());
		data.put(DURATION, waterInput.getMinutesToWater());
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		System.out.println("Update time : " + result.get().getUpdateTime());
	}

	public void findAllWaterings() throws ExecutionException, InterruptedException {
		Firestore db = getFirestore();

		ApiFuture<QuerySnapshot> query = db.collection("waterings").get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			System.out.println("id: " + document.getId());
			System.out.println("startTime: " + document.getLong(START_TIME));
			if (document.contains(DURATION)) {
				System.out.println(DURATION+ ": " + document.getLong(DURATION));
			}
			System.out.println("");
		}

	}


	public void findFutureWaterings() throws ExecutionException, InterruptedException {
		Firestore db = getFirestore();

		// Create a reference to the cities collection
		CollectionReference waterings = db.collection("waterings");
		// Create a query against the collection.
		Query query = waterings.whereGreaterThanOrEqualTo(START_TIME, new Date().getTime());
		// retrieve  query results asynchronously using query.get()
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			System.out.println("id: " + document.getId());
			System.out.println("startTime: " + document.getLong(START_TIME));
			if (document.contains(DURATION)) {
				System.out.println("Duration: " + document.getLong(DURATION));
			}
			System.out.println("");
		}

	}


}
