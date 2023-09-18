package com.planittesting.jupitertoys.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.jupitertoys.model.data.ContactData;
import com.planittesting.jupitertoys.model.pages.HomePage;
import com.planittesting.jupitertoys.tests.support.dataproviders.CsvToContactData;

class ContactTests extends BaseTest {

	@Test
	void validateMandatoryFieldsErrors() {
		var contactPage = open(HomePage.class)
							.clickContactMenu()
							.clickSubmitButton();
							
		assertAll(() -> assertEquals("Forename is required.", contactPage.getForenameError()),
				  () -> assertEquals("Email is required.", contactPage.getEmailError()),
				  () -> assertEquals("Message is required.", contactPage.getMessageError()));
	}

	@ParameterizedTest
	@CsvSource({ "Juan,,a@b.com,,Hello" })
	void validateMandatoryFieldsErrorsFix(@CsvToContactData ContactData contactData) {

		var contactPage = open(HomePage.class)
							.clickContactMenu()
							.clickSubmitButton()
							.setContactData(contactData);

		assertAll(() -> assertEquals("", contactPage.getForenameError()),
				  () -> assertEquals("", contactPage.getEmailError()),
				  () -> assertEquals("", contactPage.getMessageError()));
	}

	@Tag("data")
	@ParameterizedTest
	@CsvFileSource(resources = "/ContactData.csv", numLinesToSkip = 1)
	void validateDataformatErrors(@CsvToContactData ContactData contactData) {
		var contactPage = open(HomePage.class)
			.clickContactMenu()
			.setContactData(contactData);
		assertEquals("Length must be 10.", contactPage.getTelephoneError());
		assertEquals("Must be a valid email.", contactPage.getEmailError());
	}

	@ParameterizedTest
	@CsvSource({ 
		"Juan,Florez,a@b.com,0444888444,Hello", 
		"Someone,Else,gdgd@jfhd.com,0444888444,World" 
	})
	void validateSuccesfulContactFormSubmition(@CsvToContactData ContactData contactData) {
		var contactPage = open(HomePage.class)
			.clickContactMenu()
			.setContactData(contactData)
			.clickSubmitButton();
		assertEquals("Thanks " + contactData.forename() + ", we appreciate your feedback.", contactPage.getSuccessMessage());
	}
}
