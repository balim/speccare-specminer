package com.michaelszymczak.speccare.specminer.core;


import java.util.Arrays;
import java.util.List;

public class ExistingScenarioBuilder {
    private String name = "Default scenario";
    private List<String> scenarioContent = Arrays.asList("Scenario: Default scenario");
    private ExistingFeature wrappingFeature = ExistingFeatureBuilder.use().build();
    public static ExistingScenarioBuilder use()
    {
        return new ExistingScenarioBuilder();
    }

    public ExistingScenarioBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ExistingScenarioBuilder withContent(String... lines) {
        this.scenarioContent = Arrays.asList(lines);
        return this;
    }

    public ExistingScenarioBuilder withWrappingFeature(ExistingFeature wrappingFeature) {
        this.wrappingFeature = wrappingFeature;
        return this;
    }

    public ExistingScenario build() {
        return new ExistingScenario(name, scenarioContent, wrappingFeature);
    }
}
