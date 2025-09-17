package com.lessonpoints;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class FirebaseTest {

    private static Firestore db;

    public static void main(String[] args) throws Exception {
        // התחברות ל-Firebase
        FileInputStream serviceAccount = new FileInputStream(
                "C:\\Users\\User\\IdeaProjects\\LessonPoints\\src\\main\\java\\lessonpoints-firebase-adminsdk-fbsvc-6a7ead7acd.json"
        );
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();

        // דוגמה: יצירת סטודנט חדש
        Student s = new Student("אליאור", "יאffe", "050-1234567", "050-7654321", "1", 0);
        saveStudent(s);

        // דוגמה: הוספת נקודות על הגעה בזמן
        Points.onTime(s);
        updateStudentPoints(s);
    }

    // שמירת סטודנט חדש ב-Firestore
    public static void saveStudent(Student s) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", s.getFirstName());
        data.put("lastName", s.getLastName());
        data.put("phone", s.getPhone());
        data.put("parentPhone", s.getParentPhone());
        data.put("id", s.getId());
        data.put("points", s.getPoints());

        DocumentReference docRef = db.collection("students").document(s.getId());
        docRef.set(data);

        System.out.println("✅ סטודנט נשמר בהצלחה: " + s.getFirstName());
    }

    // עדכון נקודות סטודנט
    public static void updateStudentPoints(Student s) throws Exception {
        DocumentReference docRef = db.collection("students").document(s.getId());
        docRef.update("points", s.getPoints());
        System.out.println("✅ נקודות עודכנו: " + s.getPoints());
    }
}
