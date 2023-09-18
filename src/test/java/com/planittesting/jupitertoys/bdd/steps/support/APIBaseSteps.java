package com.planittesting.jupitertoys.bdd.steps.support;

import okhttp3.HttpUrl;

public class APIBaseSteps extends BaseSteps {

    protected HttpUrl baseURL;

    public APIBaseSteps(ObjectContainer container) {
        super(container);
        baseURL = container.retrieve("baseURL");
    }
    
}
