package com.planittesting.automation.tests.support;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.engine.execution.AfterEachMethodAdapter;
import org.junit.jupiter.engine.execution.BeforeEachMethodAdapter;
import org.junit.jupiter.engine.extension.ExtensionRegistry;

public class BeforeAndAfterParameterResolver
        implements ParameterResolver, AfterEachMethodAdapter, BeforeEachMethodAdapter {

    private Map<String, ParameterResolver> parameterisedTestParameterResolvers = new HashMap<>();

    @Override
    public void invokeBeforeEachMethod(ExtensionContext context, ExtensionRegistry registry) {
        invoke(registry, context);
    }

    @Override
    public void invokeAfterEachMethod(ExtensionContext context, ExtensionRegistry registry) throws Throwable {
        invoke(registry, context);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        if (isExecutedOnAfterOrBeforeMethod(parameterContext) && !parameterContext.getParameter().getDeclaringExecutable().getDeclaringClass().getName().contains("BaseTest")) {
            ParameterContext pContext = getMappedContext(parameterContext, extensionContext);
            return parameterisedTestParameterResolvers.get(extensionContext.getDisplayName()).supportsParameter(pContext, extensionContext);
        }
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterisedTestParameterResolvers.get(extensionContext.getDisplayName()).resolveParameter(getMappedContext(parameterContext, extensionContext),extensionContext);
    }

    private void invoke(ExtensionRegistry registry, ExtensionContext context) {
        ParameterResolver resolver = registry.getExtensions(ParameterResolver.class)
                .stream()
                .filter(parameterResolver -> parameterResolver.getClass().getName().contains("ParameterizedTestParameterResolver"))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("ParameterizedTestParameterResolver missed in the registry. Probably it's not a Parameterized Test"));
        parameterisedTestParameterResolvers.put(context.getDisplayName(), resolver);
    }

    private MappedParameterContext getMappedContext(ParameterContext parameterContext,
            ExtensionContext extensionContext) {
        return new MappedParameterContext(
                parameterContext.getIndex(),
                extensionContext.getRequiredTestMethod().getParameters()[parameterContext.getIndex()],
                Optional.of(parameterContext.getTarget()));
    }

    private boolean isExecutedOnAfterOrBeforeMethod(ParameterContext parameterContext) {
        return Arrays.stream(parameterContext.getDeclaringExecutable().getDeclaredAnnotations())
                .anyMatch(this::isAfterEachOrBeforeEachAnnotation);
    }

    private boolean isAfterEachOrBeforeEachAnnotation(Annotation annotation) {
        return annotation.annotationType() == BeforeEach.class || annotation.annotationType() == AfterEach.class;
    }
}
