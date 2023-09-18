package com.planittesting.jupitertoys.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.jupitertoys.model.data.LoginData;
import com.planittesting.jupitertoys.model.pages.HomePage;

class LoginTests extends BaseTest {

	@Test
	void validateSuccessfulLogin() {
		var user = open(HomePage.class)
			.clickLoginMenu()
			.login(new LoginData("something", "letmein"))
			.getUser();
		assertEquals("Hi something", user);
	}
}