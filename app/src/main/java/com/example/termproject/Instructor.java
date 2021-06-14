package com.example.termproject;

public class Instructor extends User {

    private boolean approved;
    private Course[] courses;

    public Instructor(int id, String emailAddress, String firstName, String lastName, String username, String password) {
        super(id, emailAddress, firstName, lastName, username, password);
    }

    public Instructor(int id, String emailAddress, String firstName, String lastName, String username, String password, Course[] courses) {
        super(id, emailAddress, firstName, lastName, username, password);
        this.courses = courses;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
