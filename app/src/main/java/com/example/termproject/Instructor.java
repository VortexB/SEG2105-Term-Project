package com.example.termproject;

public class Instructor extends User {

    private boolean approved;
    private Course[] courses;

    public Instructor(int id, String emailAddress, String firstName, String lastName, String username, String password) {
        super(id, emailAddress, firstName, lastName, username, password);
    }

    public Instructor(int id, String emailAddress, String firstName, String lastName, String username, String password, boolean approved) {
        super(id, emailAddress, firstName, lastName, username, password);
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public static Instructor fromUser(User user){
        if(user==null) return null;
        return new Instructor(user.getId(), user.getEmailAddress(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
    }
}
