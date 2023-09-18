package com.planittesting.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariFactory extends DriverFactory {
    @Override
    public SafariOptions getCapabilities() {
        var options = new SafariOptions();
        if(!version.equalsIgnoreCase("latest")) options.setCapability("browserVersion", version);
        return options;
    }

    @Override
    public WebDriver getDriver() {
        return new SafariDriver(getCapabilities());
    }
}
