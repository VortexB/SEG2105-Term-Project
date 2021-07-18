package com.example.termproject;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseDatesUnitTests {
    @Test
    public void DatesNoConflictTest(){
        assertTrue(DBHandler.timeConflict("Thursday,Friday/1:30-3:30,12:00-13:30","Thursday,Friday/4:00-6:00,15:30-17:00"));
    }
    @Test
    public void DatesConflictTest(){
        assertFalse(DBHandler.timeConflict("Thursday,Friday/1:30-3:30,12:00-13:30","Thursday,Friday/3:00-6:00,15:30-17:00"));
    }
    @Test
    public void DatesEqualTest(){
        assertFalse(DBHandler.timeConflict("Thursday,Friday/1:30-4:30,12:00-13:30","Thursday,Friday/1:30-4:30,12:00-13:30"));
    }
    @Test
    public void ValidDateTest(){
        assertTrue(DBHandler.validDate("Thursday/1:30-3:30"));
    }
    @Test
    public void InvalidDateTest(){
        assertFalse(DBHandler.validDate("July 16th 2021 at 5pm to 7pm"));
    }
}
