package com.planittesting.automation.tests.support;

import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

public class CustomJUnitParallelStrategy implements ParallelExecutionConfiguration, ParallelExecutionConfigurationStrategy {
    private int parallelism=5;

    @Override
    public ParallelExecutionConfiguration createConfiguration(ConfigurationParameters configurationParameters) {
        parallelism = configurationParameters.get("fixed.parallelism", Integer::parseInt).orElse(5);
        return this;
    }

    @Override
    public int getCorePoolSize() {
        return parallelism;
    }

    @Override
    public int getKeepAliveSeconds() {
        return 30;
    }

    @Override
    public int getMaxPoolSize() {
        return parallelism*10;
    }

    @Override
    public int getMinimumRunnable() {
        return 0;
    }

    @Override
    public int getParallelism() {
        return parallelism;
    }
}
