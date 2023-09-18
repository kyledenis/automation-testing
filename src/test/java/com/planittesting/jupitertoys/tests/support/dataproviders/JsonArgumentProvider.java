package com.planittesting.jupitertoys.tests.support.dataproviders;

import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planittesting.jupitertoys.model.data.ContactData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class JsonArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        // 1. read the json file with name ?
        var jsonFileName = context.getRequiredTestMethod().getAnnotation(WithJson.class).file();
        var jsonInputStream = getClass().getClassLoader().getResourceAsStream(jsonFileName);

        // 2. convert the json file into java objects
        ContactData[] jsonArray = new ObjectMapper().readValue(jsonInputStream, ContactData[].class);

        return Stream.of(jsonArray).map(Arguments::of);
    }
    
}
