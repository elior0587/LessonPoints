
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

import java.io.FileInputStream;
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
            FileInputStream serviceAccount = new FileInputStream(
                    "C:\\Users\\User\\IdeaProjects\\LessonPoints\\src\\main\\java\\lessonpoints-firebase-adminsdk-fbsvc-6a7ead7acd.json"
            );

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
            System.out.println("✅ Firebase initialized!");
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
                case "listen": Points.listenReason(s); break;
                case "active": Points.activeParticipation(s); break;
                case "late": Points.late(s); break;
                case "disturb": Points.disturbOnce(s); break;
                case "disturbRepeated": Points.disturbRepeated(s); break;
                case "sleep": Points.sleeping(s); break;
                case "absent": Points.fullLessonAbsence(s); break;
                case "phone": Points.phoneUse(s); break;
                case "noOrder": Points.noOrder(s); break;
                case "talk": Points.talking(s); break;
                case "leave": Points.leaveLesson(s); break;
                default: return "❌ Unknown action";
            }

            docRef.update("points", s.getPoints());
            return "✅ Points updated: " + s.getPoints();
        }
    }

// שים פה את המחלקות Student ו-Points כמו ששלחת קודם, באותו package


