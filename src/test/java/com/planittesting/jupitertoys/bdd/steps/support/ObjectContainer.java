package com.planittesting.jupitertoys.bdd.steps.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

public class ObjectContainer {

	private WebDriver driver;
	private Map<String, Object> context = new HashMap<String, Object>();

	public <V> void register(String key, V value) {
		context.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <V> V retrieve(String key) {
		return (V) context.get(key);
	}
	
	public Optional<WebDriver> getDriver() {
		return Optional.ofNullable(driver);
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
