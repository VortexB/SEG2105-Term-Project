package com.example.termproject;

public class Course {

    String courseCode;
    private String name;
    private int studentCapacity;
    private String courseDescription;
    private String dates;
    private Instructor instructor;


    public Course(String courseCode, String name, int studentCapacity, String courseDescription, String dates, Instructor instructor) {
        this.courseCode = courseCode;
        this.name = name;
        this.studentCapacity = studentCapacity;
        this.courseDescription = courseDescription;
        this.dates = dates;
        this.instructor = instructor;
    }

    public Course(String courseCode, String name) {
        this.courseCode = courseCode;
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
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

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    public String getDays(){
        if(dates!=null) {
            String[] spiltDates_Days_Times = dates.split("/");
            if (spiltDates_Days_Times.length > 1) {
                return spiltDates_Days_Times[0];
            }
        }
        return null;
    }
    public String getTimes(){
        if(dates!=null) {
            String[] spiltDates_Days_Times = dates.split("/");
            if (spiltDates_Days_Times.length > 1) {
                return spiltDates_Days_Times[1];
            }
        }
        return null;
    }
}
