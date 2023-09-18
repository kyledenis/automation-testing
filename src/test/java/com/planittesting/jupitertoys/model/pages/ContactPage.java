package com.planittesting.jupitertoys.model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import com.planittesting.automation.driver.support.CleanWebDriverWait;
import com.planittesting.automation.driver.support.CustomActions;
import com.planittesting.jupitertoys.model.data.ContactData;

public class ContactPage extends BasePage<ContactPage> {

	public ContactPage(WebDriver driver) {
		super(driver);
	}

	public ContactPage clickSubmitButton() {
		var element = driver.findElement(By.id("submit-button"));
		new CustomActions(driver).moveToElement(element).click().build().perform();
		return this;
	}

	// TODO get an id or class in the text element 
	private String getErrorText(By by) {
		var elements = driver.findElements(by);
		return elements.size()>0 ? elements.get(0).getText() : "";
	}

	public String getForenameError() {
		return getErrorText(By.id("forename-err"));
	}

	public String getEmailError() {
		return getErrorText(By.id("email-err"));
	}

	public String getMessageError() {
		return getErrorText(By.id("message-err"));
	}

	public String getTelephoneError() {
		return getErrorText(By.id("telephone-err"));
	}

	public ContactPage setForename(String name) {
		getForenameElement().sendKeys(name);
		return this;
	}

	private WebElement getForenameElement() {
		return driver.findElement(By.id("forename"));
	}

	public ContactPage setEmail(String email) {
		getEmailElement().sendKeys(email);
		return this;
	}

	private WebElement getEmailElement() {
		return driver.findElement(By.id("email"));
	}

	public ContactPage setMessage(String message) {
		getMessageElement().sendKeys(message);
		return this;
	}

	private WebElement getMessageElement() {
		return driver.findElement(By.id("message"));
	}

	public ContactPage setTelephone(String telephone) {
		getTelephoneElement().sendKeys(telephone);
		return this;
	}

	private WebElement getTelephoneElement() {
		return driver.findElement(By.id("telephone"));
	}

	public ContactPage setSurname(String surname) {
		getSurnameElement().sendKeys(surname);
		return this;
	}

	private WebElement getSurnameElement() {
		return driver.findElement(By.id("surname"));
	}

	public String getSuccessMessage() {
		return new CleanWebDriverWait(driver, Duration.ofSeconds(120))
				  .until(d->d.findElement(By.id("success-message"))).getText();
	}

	public ContactPage setContactData(ContactData contactData) {
		if(contactData.forename() != null) setForename(contactData.forename());
		if(contactData.surname() != null) setSurname(contactData.surname());
		if(contactData.email() != null) setEmail(contactData.email());
		if(contactData.telephone() != null) setTelephone(contactData.telephone());
		if(contactData.message() != null) setMessage(contactData.message());
		return this;
	}

}
