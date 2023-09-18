package com.planittesting.jupitertoys.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;
import com.planittesting.jupitertoys.bdd.steps.support.WebBaseSteps;
import com.planittesting.jupitertoys.model.components.dialogs.LoginDialog;
import com.planittesting.jupitertoys.model.data.LoginData;
import com.planittesting.jupitertoys.model.pages.HomePage;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends WebBaseSteps {

	public LoginSteps(ObjectContainer container) {
		super(container);
	}

	@Given("A valid user")
	public void givenAValidUser() {
		var username = "anyone";
		var password = "letmein";
		container.register("login", new LoginData(username,password));
	}

	@When("User logs in to the application")
	public void whenUserLogsInToTheApplication() {
		LoginData loginData = container.retrieve("login");
		open(HomePage.class)
			.clickLoginMenu()
			.login(loginData);
	}

	@Then("User should see the welcome message")
	public void thenUserShouldSeeTheWelcomeMessage() {
		LoginData loginData = container.retrieve("login");
		assertEquals("Hi "+loginData.username(), open(HomePage.class).getUser());
	}

	@Given("An invalid user")
	public void givenAnInvalidUser() {
		var username = "anyone";
		var password = "wrongpassword";
		container.register("login", new LoginData(username, password));
	}

	@SuppressWarnings("rawtypes")
	@Then("User should see the incorrect login error message")
	public void thenUserShouldSeeTheIncorrectLoginErrorMessage() {
		LoginDialog loginDialog = container.retrieve("login dialog");
		assertEquals("Your login details are incorrect", loginDialog.getErrorMessage());
	}
}