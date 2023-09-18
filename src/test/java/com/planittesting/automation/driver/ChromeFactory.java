package com.planittesting.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeFactory extends DriverFactory {
    @Override
    public ChromeOptions getCapabilities() {
        var options = new ChromeOptions();
        if(headless) options.addArguments("--headless=new");
        options.addArguments("--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors");
        if(!version.equalsIgnoreCase("latest")) options.setCapability("browserVersion", version);
        return options;
    }

    @Override
    public WebDriver getDriver() {
        return new ChromeDriver(getCapabilities());
    }
}