package com.example.termproject;

public class Course {

    private int courseCode;
    private String name;
    private int studentCapacity;
    private String courseDescription;
    private CourseDate[] dates;
    private Instructor instructor;

    public Course(int courseCode, String name, int studentCapacity, String courseDescription, CourseDate[] dates, Instructor instructor) {
        this.courseCode = courseCode;
        this.name = name;
        this.studentCapacity = studentCapacity;
        this.courseDescription = courseDescription;
        this.dates = dates;
        this.instructor = instructor;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentCapacity() {
        return studentCapacity;
    }

    public void setStudentCapacity(int studentCapacity) {
        this.studentCapacity = studentCapacity;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public CourseDate[] getDates() {
        return dates;
    }

    public void setDates(CourseDate[] dates) {
        this.dates = dates;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
