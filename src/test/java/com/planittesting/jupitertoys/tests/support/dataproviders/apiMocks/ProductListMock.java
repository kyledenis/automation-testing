package com.planittesting.jupitertoys.tests.support.dataproviders.apiMocks;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.planittesting.jupitertoys.tests.BaseTest;
import com.planittesting.jupitertoys.tests.support.dataproviders.WithJson;
import com.planittesting.jupitertoys.tests.support.dataproviders.apiMocks.networkInterceptors.JsonHttpInterceptor;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * At the time this class was created only Chrome supported the WebDriver BiDi protocol
 * This is why there is a check for skipping the test invocation
 * If SELENIUM_BROWSER contains anything other than chrome then a test that uses BiDi will be skipped
 * Once BiDi has been fully implemented in a cross browser way the skip check must be removed
 */
public class ProductListMock implements BeforeTestExecutionCallback {
    private String json;

    /**
     * This constructor is for use with @ExtendsWith
     * Also requires a @WithJson annotation to pass the json payload to the interceptor
     */
    public ProductListMock() {}

    /**
     * This consturctor is for use with @RegisterExtension
     * @param json
     */
    public ProductListMock(String json) {
        this.json = json;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        var baseTest = (BaseTest)context.getTestInstance().get();
        
        // TODO once Bidi is supported on other browsers remove these checks
        var browser = baseTest.getBrowser();
        assumeTrue(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("edge"), "[INTERCEPT] browser '"+browser+"' not yet supported, skipping test");
        
        var requiredTestMethod = context.getRequiredTestMethod();
        json = (json == null && requiredTestMethod.getAnnotation(WithJson.class)!=null)
            ? requiredTestMethod.getAnnotation(WithJson.class).value()
            : baseTest.getClass().getAnnotation(WithJson.class).value();
        new JsonHttpInterceptor().intercept(baseTest.getDriver(), json, "/products", 200);
    }     
}


