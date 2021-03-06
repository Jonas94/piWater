package com.example.piwater.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.*;
import org.slf4j.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;

import java.io.*;

@Repository
public class FirebaseConnector {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConnector.class);

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
