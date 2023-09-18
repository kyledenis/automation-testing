package com.planittesting.automation.driver.support;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CustomActions extends Actions {
    private WebDriver driver;

    public CustomActions(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Override
    public Actions moveToElement(WebElement target) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", target);
        return super.moveToElement(target);
    }
}
