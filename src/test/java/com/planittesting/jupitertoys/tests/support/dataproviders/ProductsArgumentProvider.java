package com.planittesting.jupitertoys.tests.support.dataproviders;

import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planittesting.jupitertoys.model.data.ProductData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class ProductsArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        var json = context.getRequiredTestMethod().getAnnotation(WithJson.class).value();
        var jsonObject = new ObjectMapper().readValue(json, new TypeReference<List<ProductData>>(){});
        return Stream.of(jsonObject).map(Arguments::of);
    }
}
