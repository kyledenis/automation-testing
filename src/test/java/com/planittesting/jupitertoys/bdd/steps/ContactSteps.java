package com.planittesting.jupitertoys.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;
import com.planittesting.jupitertoys.bdd.steps.support.WebBaseSteps;
import com.planittesting.jupitertoys.model.data.ContactData;
import com.planittesting.jupitertoys.model.pages.ContactPage;
import com.planittesting.jupitertoys.model.pages.HomePage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactSteps extends WebBaseSteps {

	public ContactSteps(ObjectContainer container) {
		super(container);
	}

	@Given("A Customer tries to contact us")
	public void GivenACustomerTriesToContactUs() {
		open(HomePage.class).clickContactMenu();
	}

	@When("Customer enters invalid Email id and invalid Telephone")
	public void WhenCustomerEntersInvalidEmailIdAndInvalidTelephone() throws Throwable {
		var contactData = new ContactData(null, null, "aaaa","sgsgsgs",null) ;
		container.register("InvalidEmailAndTelephone", contactData);
		open(ContactPage.class)
			.setEmail(contactData.email())
			.setTelephone(contactData.telephone());
	}

	@Then("Error message for invalid email and invalid telephone are displayed")
	public void ThenErrorMessageForInvalidEmailAndInvalidTelephoneAreIsDisplayed() {
		var contactPage = open(ContactPage.class);
		assertEquals("Must be a valid email.", contactPage.getEmailError());
		assertEquals("Length must be 10.", contactPage.getTelephoneError());
	}

	@When("Customer submits empty information")
	public void customerSubmitsEmptyInformation() {
		open(ContactPage.class)
			.clickSubmitButton();
	}

	@Then("Errors for mandatory information are displayed")
	public void ErrorsForMandatoryInformationAreDisplayed() {
		var contactPage = open(ContactPage.class);
		assertAll(
			() -> assertEquals("Forename is required.", contactPage.getForenameError()),
			() -> assertEquals("Email is required.", contactPage.getEmailError()),
			() -> assertEquals("Message is required.", contactPage.getMessageError()));
	}

	@When("Customer submits all mandatory information")
	public void customerSubmitsAllMandatoryInformation(DataTable table) {
		var contactData = table.asMaps(String.class, String.class).stream().map(entry -> new ContactData(
			entry.get("ForeName").toString(), 
			null,  
			entry.get("Email").toString(),
			entry.get("Telephone").toString(),
			entry.get("Message").toString())).findFirst().get();
		
		container.register("ValidContactData", contactData);
		open(ContactPage.class)
			.setContactData(contactData)
			.clickSubmitButton();
	}

	@Then("Acknowledgement message is displayed")
	public void acknowledgement_message_is_displayed() {
		ContactData contactData = container.retrieve("ValidContactData");
		var contactPage = open(ContactPage.class);
		assertEquals("Thanks " + contactData.forename() + ", we appreciate your feedback.",
				contactPage.getSuccessMessage());
	}
}