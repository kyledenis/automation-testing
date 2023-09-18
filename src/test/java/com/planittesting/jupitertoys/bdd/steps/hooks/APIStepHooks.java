package com.planittesting.jupitertoys.bdd.steps.hooks;

import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;

import io.cucumber.java.Before;
import okhttp3.HttpUrl;

public class APIStepHooks {

    private ObjectContainer objectContainer;

    public APIStepHooks(ObjectContainer objectContainer) {
        this.objectContainer = objectContainer;
    }

    @Before("@api")
    public void setup() {
        // TODO: use EnvironmentVariables.getApiBaseUrl()
        var baseURL = HttpUrl.parse("https://petstore3.swagger.io/api/v3");
        objectContainer.register("baseURL", baseURL);
    }
    
}
