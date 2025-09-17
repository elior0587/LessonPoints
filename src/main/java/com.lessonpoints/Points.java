package com.lessonpoints;

public class Points {
        public static void onTime(Student s) { s.points += 1; }             // הגעה בזמן – 1 נקודה
        public static void listenReason(Student s) { s.points += 3; }       // הקשבה פייסיבית – 3 נקודות
        public static void activeParticipation(Student s) { s.points += 3; }// השתתפות אקטיבית – 3 נקודות
        // נקודות לחובה
        public static void fullLessonAbsence(Student s) { s.points -= 10; } // העדרות מלאה – 10-
        public static void phoneUse(Student s) { s.points -= 3; }           // החזקת טלפון – 3-
        public static void noOrder(Student s) { s.points -= 2; }            // אין ישיבה מסודרת – 2-
        public static void disturbOnce(Student s) { s.points -= 2; }        // הפרעה חד-פעמית – 2-
        public static void disturbRepeated(Student s) { s.points -= 4; }    // הפרעה חוזרת – 4-
        public static void late(Student s) { s.points -= 1; }               // איחור – 1-
        public static void leaveLesson(Student s) { s.points -= 2; }        // יציאה מהשיעור – 2-
        public static void talking(Student s) { s.points -= 1; }            // דיבור בשיעור – 1-
        public static void sleeping(Student s) { s.points -= 3; }

}
