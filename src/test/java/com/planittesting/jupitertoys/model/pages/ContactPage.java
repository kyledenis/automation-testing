package com.planittesting.jupitertoys.model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
    }
    
    public ContactPage setEmail(String email) {
        driver.findElement(By.id("email")).sendKeys(email);
        return this;
    }

    public String getEmailError() {
        return driver.findElement(By.id("email-err")).getText();
    }
}
