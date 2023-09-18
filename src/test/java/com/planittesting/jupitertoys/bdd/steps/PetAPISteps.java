package com.planittesting.jupitertoys.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.json.bind.JsonbBuilder;

import com.planittesting.jupitertoys.bdd.steps.support.APIBaseSteps;
import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;
import com.planittesting.jupitertoys.model.api.swaggerPetstore.PetAPI;
import com.planittesting.jupitertoys.model.api.swaggerPetstore.data.Pet;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PetAPISteps extends APIBaseSteps {

    public PetAPISteps(ObjectContainer container) {
        super(container);
    }

    @Given("a new pet is created")
    public void aNewPetIsCreated(String json) throws IOException {
        var petAPI = new PetAPI(baseURL);
        var actualPet = petAPI.createPet(json);
        var expectedPet = JsonbBuilder.create().fromJson(json, Pet.class);
        container.register("actualPet", actualPet);
        container.register("expectedPet", expectedPet);
    }

    @Then("the new pet details match the details submitted")
    public void theNewPetDetailsMatchTheDetailsSubmitted() {
        Pet actualPet = container.retrieve("actualPet");
        Pet expectedPet = container.retrieve("expectedPet");
        assertEquals(expectedPet, actualPet);
    }
    
}
