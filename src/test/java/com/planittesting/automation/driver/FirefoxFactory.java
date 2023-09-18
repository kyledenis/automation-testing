package com.planittesting.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxFactory extends DriverFactory {
    @Override
    public FirefoxOptions getCapabilities() {
        var firefoxProfile = new FirefoxProfile();
        //these preferences are required for CDP to work in Firefox. See https://firefox-source-docs.mozilla.org/remote/cdp/RequiredPreferences.html
        firefoxProfile.setPreference("fission.bfcacheInParent", false);
        firefoxProfile.setPreference("fission.webContentIsolationStrategy",0);
        var options = new FirefoxOptions();
        options.setLogLevel(FirefoxDriverLogLevel.FATAL);
        if (headless) options.addArguments("-headless");
        options.addArguments("-width 1920", "-height 1200");
        options.setProfile(firefoxProfile);
        if(!version.equalsIgnoreCase("latest")) options.setCapability("browserVersion", version);
        return options;
    }

    @Override
    public WebDriver getDriver() {
        return new FirefoxDriver(getCapabilities());
    }
}