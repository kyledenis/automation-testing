package com.planittesting.automation.driver.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class DriverLogListener implements WebDriverListener {

	private static final Logger logger = LoggerFactory.getLogger(DriverLogListener.class.getSimpleName());
    private final String testClass;
    private final String testMethod;


    public DriverLogListener(String testClass, String testMethod) {
        this.testClass = testClass;
        this.testMethod = testMethod;
    }

    @Override
    public void beforeFindElement(WebElement element, By by) {
        logger.info("{}::{} => Trying to find element located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void afterFindElement(WebElement element, By by, WebElement result) {
        var found = element == null ? "Couldn't find" : "Found";
        logger.info("{}::{} => " + found + " element located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By by) {
        logger.info("{}::{} => Trying to find element located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void afterFindElement(WebDriver driver, By by, WebElement result) {
        var found = result == null ? "Couldn't find" : "Found";
        logger.info("{}::{} => " + found + " element located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void beforeFindElements(WebDriver driver, By by) {
        logger.info("{}::{} => Trying to find elements located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void beforeFindElements(WebElement element, By by) {
        logger.info("{}::{} => Trying to find elements located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void afterFindElements(WebDriver driver, By by, List<WebElement> result) {
        var found = result.size()==0 ? "Couldn't find" : "Found "+result.size();
        logger.info("{}::{} => " + found + " elements located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void afterFindElements(WebElement element, By by, List<WebElement> result) {
        var found = result.size()==0 ? "Couldn't find" : "Found "+result.size();
        logger.info("{}::{} => " + found + " elements located "+by.toString(), testClass, testMethod);
    }

    @Override
    public void beforeClick(WebElement element) {
        var driver = ((WrapsDriver)element).getWrappedDriver();
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Override
    public void afterClick(WebElement element) {
        logger.info("{}::{} => Clicked on element ", testClass, testMethod);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        if(keysToSend != null) {
            logger.info("{}::{} => Sent text: '"+String.join("", keysToSend)+"' to element", testClass, testMethod);
        } else {
            logger.info("{}::{} => Cleared text on element", testClass, testMethod);
        }
    }

    @Override
    public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
    }

    @Override
    public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException throwable) {
        logger.info("{}::{} => Error: " + throwable.getCause(), testClass, testMethod);
    }

    @Override
    public void beforeGetText(WebElement element) {
    }

    @Override
    public void afterGetText(WebElement element, String text) {
        logger.info("{}::{} => Read text: '"+text+"' from element", testClass, testMethod);
    }

}
