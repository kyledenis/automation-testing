package com.planittesting.jupitertoys.tests.support.dataproviders.apiMocks.networkInterceptors;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHttpInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JsonHttpInterceptor.class.getSimpleName());
    
    public void intercept(WebDriver driver, String json, String uriFilter, int status) {
        @SuppressWarnings({ "resource", "unused" })
        var interceptor = new NetworkInterceptor(driver,
            Route.matching(req -> req.getUri().contains(uriFilter))
                .to(() -> req -> { 
                    logger.info("[INTERCEPT] mock reponse to request '"+req.getUri()+"' with payload:\n"+json);
                    return new HttpResponse()
                        .setStatus(status)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Access-Control-Allow-Origin", "*")
                        .addHeader("Access-Control-Allow-Methods", "OPTIONS,POST,PUT,DELETE,GET")
                        .addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, x-api-key")
                        .setContent(() -> new ByteArrayInputStream(json.getBytes()));
                }
            )
        );
    }
}
