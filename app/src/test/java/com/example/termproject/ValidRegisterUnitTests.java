package com.example.termproject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidRegisterUnitTests {
    @Test
    public void alphabeticalValidTest(){
        assertTrue(RegisterActivity.onlyAlpha("Hello"));
    }
    @Test
    public void alphabeticalInvalidTest(){
        assertFalse(RegisterActivity.onlyAlpha("He110"));
    }
    @Test
    public void emailValidTest(){
        assertTrue(RegisterActivity.validEmail("name@email.com"));
    }
    @Test
    public void emailInvalidTest(){
        assertFalse(RegisterActivity.validEmail("name%email.com"));
    }
    @Test
    public void usernameValidTest(){
        assertTrue(RegisterActivity.validUsername("user123"));
    }
    @Test
    public void usernameInvalidTest(){
        assertFalse(RegisterActivity.validUsername("ur"));
    }

}
