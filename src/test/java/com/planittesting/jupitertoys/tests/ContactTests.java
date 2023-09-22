package com.planittesting.jupitertoys.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.planittesting.jupitertoys.model.pages.HomePage;

public class ContactTests extends BaseTest {
    
    @Test
    void validateEmailerror() {
        var errorMessage = open(HomePage.class)
            .clickContactMenu()
            .setEmail("bogota")
            .getEmailError();

        assertEquals("Must be a valid email.", errorMessage);
    }

}
