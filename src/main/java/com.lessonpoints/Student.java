package com.lessonpoints;

public class Student {
    private String firstName;
    private String lastName;
    private String phone;
    private String parentPhone;
    private String id;
    public int points;

    public Student() {}

    public Student(String firstName, String lastName, String phone, String parentPhone, String id, int points){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.id = id;
        this.points = points;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPoints() {
        return points;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
