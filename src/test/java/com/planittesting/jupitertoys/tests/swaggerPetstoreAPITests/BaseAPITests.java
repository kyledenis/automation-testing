package com.planittesting.jupitertoys.tests.swaggerPetstoreAPITests;

import org.junit.jupiter.api.BeforeEach;

import okhttp3.HttpUrl;

public class BaseAPITests {
    protected HttpUrl baseURL;
    
    @BeforeEach
    public void setup() {
        // TODO: use EnvironmentVariables.getApiBaseUrl()
        baseURL = HttpUrl.parse("https://petstore3.swagger.io/api/v3");
    }
}
