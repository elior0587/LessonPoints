package com.lessonpoints;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class StudentPointsAPI implements CommandLineRunner {

    private Firestore db;

    public static void main(String[] args) {
        SpringApplication.run(StudentPointsAPI.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String firebaseConfig = System.getenv("FIREBASE_CONFIG");

        if (firebaseConfig == null) {
            throw new IllegalStateException("FIREBASE_CONFIG environment variable is missing");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8))
                ))
                .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
        System.out.println("✅ Firebase initialized from environment!");
    }

    @PostMapping("/student")
    public String saveStudent(@RequestBody Student s) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", s.getFirstName());
        data.put("lastName", s.getLastName());
        data.put("phone", s.getPhone());
        data.put("parentPhone", s.getParentPhone());
        data.put("id", s.getId());
        data.put("points", s.getPoints());

        DocumentReference docRef = db.collection("students").document(s.getId());
        docRef.set(data);

        return "✅ Student saved: " + s.getFirstName();
    }

    @PostMapping("/student/{id}/points")
    public String updatePoints(@PathVariable String id, @RequestParam String action) throws Exception {
        DocumentReference docRef = db.collection("students").document(id);
        Student s = docRef.get().get().toObject(Student.class);
        if (s == null) return "❌ Student not found";

        switch (action) {
            case "onTime": Points.onTime(s); break;
        }

        docRef.update("points", s.getPoints());
        return "✅ Points updated: " + s.getPoints();
    }

}
