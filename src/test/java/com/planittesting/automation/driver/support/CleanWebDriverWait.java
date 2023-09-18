package com.planittesting.automation.driver.support;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CleanWebDriverWait extends WebDriverWait {

    public CleanWebDriverWait(WebDriver driver, Duration timeout) {
        super(driver, timeout);
    }

    @Override
    public <V> V until(Function<? super WebDriver, V> isTrue) {
        var driver = super.until(d -> d);
        var wait = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        var value = super.until(isTrue);
        driver.manage().timeouts().implicitlyWait(wait);
        return value;
    }
    
}
