package com.example.termproject;

import java.util.Date;

public class CourseDate {

    private SimpleDate startDate;
    private SimpleDate endDate;

    public CourseDate(SimpleDate startDate, SimpleDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SimpleDate getStartDate() {
        return startDate;
    }

    public void setStartDate(SimpleDate startDate) {
        this.startDate = startDate;
    }

    public SimpleDate getEndDate() {
        return endDate;
    }

    public void setEndDate(SimpleDate endDate) {
        this.endDate = endDate;
    }
}
