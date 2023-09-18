package com.planittesting.jupitertoys.bdd.steps.support;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

public abstract class WebBaseSteps extends BaseSteps {

	protected WebDriver driver;

	public WebBaseSteps(ObjectContainer container) {
		super(container);
		this.driver = container.getDriver().get();
	}

	protected <T> T open(Class<T> clazz) {
        try {
            return clazz.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
