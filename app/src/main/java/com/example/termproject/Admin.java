package com.example.termproject;

public class Admin extends User{

    private static User[] pendingInstructors;
    public Admin(int id, String emailAddress, String firstName, String lastName, String username, String password) {
        super(id, emailAddress, firstName, lastName, username, password);
    }

    public static User[] getPendingInstructors() {
        return pendingInstructors;
    }

    public static void setPendingInstructors(User[] pendingInstructors) {
        Admin.pendingInstructors = pendingInstructors;
    }
}
