package com.planittesting.automation.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.reflections.Reflections;

public abstract class DriverFactory {
    public abstract Capabilities getCapabilities();
    public abstract WebDriver getDriver();

    // protected String browser;
    protected boolean headless = true;
    protected Optional<URL> gridUrl = Optional.ofNullable(null);
    protected String version = "latest";

    public DriverFactory() {
        // this.browser = browser;
    }

    public DriverFactory withHeadless(boolean headless) {
        this.headless = headless;
        return this;
    }

    public DriverFactory withVersion(String version) {
        this.version = version;
        return this;
    }

    public DriverFactory withGridUrl(String gridUrl) throws MalformedURLException {
        URL url = gridUrl != null && !gridUrl.isEmpty()? new URL(gridUrl) : null;
        this.gridUrl = Optional.ofNullable(url);
        return this;
    }

    public WebDriver build() throws Exception {
        var driver = gridUrl
            .map(url -> (WebDriver)new RemoteWebDriver(url, this.getCapabilities()))
            .orElseGet(() -> this.getDriver());
        return new Augmenter().augment(driver);
        // Removed for now as it seems it is not needed with selenium 4.3.0
        // TODO once Bidi is supported on other browsers remove the browser check
        // if(driver.getClass().getSimpleName().contains("RemoteWebDriver") && (this.getClass().getSimpleName().toLowerCase().contains("chrome") || this.getClass().getSimpleName().toLowerCase().contains("edge"))) {
        //     ((HasDevTools)driver).getDevTools().createSession();
        // }
    }

    /**
     * Creates an instance of a DriverFactory 
     * and calls the getDriver methodd to instantiate the WebDriver
     * @param browser Name of the browser to instantiate
     * @return DriverFactory instance of type hinted by the browser parameter
     * @throws Exception Thrown if the browser is not yet supported. To add support for a new browser create a new class that extends DriverFactory
     */
    public static DriverFactory getFactory(String browser) throws Exception {
        var driverClasses = new ArrayList<>(
                new Reflections(DriverFactory.class.getPackageName()).getSubTypesOf(DriverFactory.class));
        return driverClasses.stream()
                .filter(driverClass -> driverClass.getSimpleName().toLowerCase().contains(browser.toLowerCase())).findFirst()
                .orElseThrow(() -> new RuntimeException("Browser: '" + browser + "'' is not a supported browser"))
                .getConstructor()
                .newInstance();
    }
}