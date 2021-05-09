package com.example.piwater.db;
import com.example.piwater.service.*;
import com.google.api.core.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Repository
public class FirebaseConnector
{

	public static void initializeFireStore() throws IOException {

		ClassPathResource resource = new ClassPathResource("waterbutler-7b556-cac4931051f1.json");
		InputStream inputStream = resource.getInputStream();
		GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.build();
		FirebaseApp.initializeApp(options); //TODO: only do this once at startup!
	}

	public Firestore getFirestore() throws IOException {

		Firestore db = FirestoreClient.getFirestore();


		return db;

	}


	public void addDataToFirestore(WaterInput waterInput) throws IOException, ExecutionException, InterruptedException {
		Firestore db = getFirestore();
		DocumentReference docRef = db.collection("waterings").document();
		// Add document data  with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("startTime", waterInput.getStartDateAsLong());
		data.put("duration", waterInput.getMinutesToWater());
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		System.out.println("Update time : " + result.get().getUpdateTime());
	}

}
