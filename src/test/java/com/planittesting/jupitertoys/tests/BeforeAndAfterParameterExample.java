package com.planittesting.jupitertoys.tests;

import com.planittesting.automation.tests.support.BeforeAndAfterParameterResolver;
import com.planittesting.jupitertoys.model.data.ContactData;
import com.planittesting.jupitertoys.tests.support.dataproviders.CsvToContactData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ExtendWith(BeforeAndAfterParameterResolver.class)
public class BeforeAndAfterParameterExample extends BaseTest {
    @BeforeEach
    public void init(ContactData contactData) {
        System.out.println("before: "+contactData.forename());
    }

    @Tag("example")
    @ParameterizedTest
    @CsvSource({ "Juan,,a@b.com,,Hello" })
    public void test(@CsvToContactData ContactData contactData) {
        System.out.println("Test: "+contactData.forename());
    }

    @AfterEach
    public void end(ContactData contactData) {
        System.out.println("after: "+contactData.forename());
    }
}
