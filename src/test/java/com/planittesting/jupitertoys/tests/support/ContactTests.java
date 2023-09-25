package com.planittesting.jupitertoys.tests.support;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.planittesting.jupitertoys.tests.BaseTest;
import com.planittesting.jupitertoys.model.pages.HomePage;
import com.planittesting.jupitertoys.model.pages.ContactPage;

public class ContactTests extends BaseTest{
    @Test
    void validateEmailError() {
        var homePage = new HomePage(driver);
        homePage.clickContactMenu();
        var contactPage = new ContactPage(driver);
        contactPage.setEmail("test");
        var error = contactPage.getEmailErrorMessage();
        assertEquals("Must be a valid email.", error);
    }
}
