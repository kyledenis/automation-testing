package com.planittesting.jupitertoys.model.components.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.planittesting.jupitertoys.model.data.LoginData;

public class LoginDialog<T> {

	private WebElement rootElement;
	private T parent;

	public LoginDialog(WebElement rootElement, T parent) {
		this.rootElement = rootElement;
		this.parent = parent;
	}

	public LoginDialog<T> setUsername(String username) {
		rootElement.findElement(By.id("username")).sendKeys(username);
		return this;
	}

	public LoginDialog<T> setPassword(String password) {
		rootElement.findElement(By.id("password")).sendKeys(password);
		return this;
	}

	public T clickLoginButton() {
		getLoginButtonElement().click();
		return parent;
	}

	private WebElement getLoginButtonElement() {
		return rootElement.findElement(By.id("modal-log-in-button"));
	}
	
	public LoginDialog<T> clickAgreeCheckbox() {
		rootElement.findElement(By.id("agree")).click();
		return this;
	}
	
	public T login(LoginData loginData) {
		return this.setUsername(loginData.username())
			.setPassword(loginData.password())
			.clickAgreeCheckbox()
			.clickLoginButton();
	}

	public String getErrorMessage() {
		return rootElement.findElement(By.id("login-warning")).getText();
	}
}