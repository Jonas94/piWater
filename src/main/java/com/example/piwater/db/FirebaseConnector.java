package com.example.piwater.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class FirebaseConnector {

    public static void initializeFireStore(String configPath) throws IOException {
        ClassPathResource resource = new ClassPathResource(configPath);
        InputStream inputStream = resource.getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }

    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}
