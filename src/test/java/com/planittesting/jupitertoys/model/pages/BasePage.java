package com.planittesting.jupitertoys.model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * <h1>BasePage</h1>
 * <p>
 * {@code BasePage} is an abstract class which contains common
 * page methods for objects that are persistently present on
 * most pages in the application. Page objects that extend this 
 * class should ensure that the common methods are executable 
 * within the page on the application.
 * </p>
 * 
 * <p>
 * <h2>Example</h2>
 * <b>Navbars</b> are a good example of these objects as they 
 * are usually visible/present on most pages in an application.
 * The common methods for this would be clicking on menu items
 * to navigate to a {@code new Page()}.
 * </p>
 * 
 * <h2>Note</h2>
 * <p>
 * If a page in the application cannot execute the common methods
 * in this class, do not extend this class on the page object.
 * However if it is part of a group of pages with similar methods,
 * create a new abstract base page and extend those page objects
 * from there.
 * </p>
 */
public abstract class BasePage {

	protected final WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public ContactPage clickContactMenu() {
		driver.findElement(By.id("menu-contact")).click();
		return new ContactPage(driver);
	}
}
