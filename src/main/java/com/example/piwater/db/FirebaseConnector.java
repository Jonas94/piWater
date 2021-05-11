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
import java.util.*;
import java.util.concurrent.*;

@Repository
public class FirebaseConnector
{
	public static final String START_TIME = "startTime";
	public static final String DURATION = "duration";
	public static final String WATERING = "watering";

	private static final Logger log = LoggerFactory.getLogger(FirebaseConnector.class);


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
		return FirestoreClient.getFirestore();
	}


	public void addDataToFirestore(WaterInput waterInput) throws ExecutionException, InterruptedException {
		Firestore db = getFirestore();
		DocumentReference docRef = db.collection(WATERING).document(waterInput.getStartDate().toString());

		Map<String, Object> data = new HashMap<>();
		data.put(START_TIME, waterInput.getStartDateAsLong());
		data.put(DURATION, waterInput.getMinutesToWater());
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);

		log.info("Data written to firebase!");
	}

	public List<Watering> findAllWaterings() throws ExecutionException, InterruptedException {
		Firestore db = getFirestore();

		return findWateringsWithQuery(db.collection(WATERING));
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
			if (document.contains(DURATION)) {
				watering.setDuration(Math.toIntExact(document.getLong(DURATION)));
			}
			waterings.add(watering);
		}
		return waterings;
	}


}
