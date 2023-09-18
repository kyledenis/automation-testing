package com.planittesting.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeFactory extends DriverFactory {
    @Override
    public EdgeOptions getCapabilities() {
        var options = new EdgeOptions();
        if(headless) options.addArguments("--headless=new");
        options.addArguments("--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors");
        if(!version.equalsIgnoreCase("latest")) options.setCapability("browserVersion", version);
        return options;
    }

    @Override
    public WebDriver getDriver() {
        return new EdgeDriver(getCapabilities());
    }

}