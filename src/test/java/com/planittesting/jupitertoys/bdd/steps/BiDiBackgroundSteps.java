package com.planittesting.jupitertoys.bdd.steps;


import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;
import com.planittesting.jupitertoys.bdd.steps.support.WebBaseSteps;
import com.planittesting.jupitertoys.tests.support.dataproviders.apiMocks.networkInterceptors.JsonHttpInterceptor;

import io.cucumber.java.en.Given;

public class BiDiBackgroundSteps extends WebBaseSteps {

    public BiDiBackgroundSteps(ObjectContainer container) {
        super(container);
    }
    
    @Given("^product API mock with payload$")
    public void productApiMock(String json) {
        String browser = container.retrieve("browser");
        assumeTrue(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("edge"), "[INTERCEPT] browser '"+browser+"' not yet supported, skipping test");
        new JsonHttpInterceptor().intercept(container.getDriver().get(), json, "/products", 200);
    }
}
