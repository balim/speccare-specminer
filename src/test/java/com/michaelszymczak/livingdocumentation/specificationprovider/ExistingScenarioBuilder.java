package com.michaelszymczak.livingdocumentation.specificationprovider;


import com.michaelszymczak.livingdocumentation.domain.ExistingFeature;
import com.michaelszymczak.livingdocumentation.domain.ExistingScenario;

import java.util.Arrays;
import java.util.List;

public class ExistingScenarioBuilder {
    private final TextFragmentProvider tfp = new TextFragmentProvider();
    private List<String> scenarioContent = Arrays.asList("Scenario: Default scenario");
    private ExistingFeature wrappingFeature = ExistingFeatureBuilder.use().build();

    public static ExistingScenarioBuilder use()
    {
        return new ExistingScenarioBuilder();
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
        return new ExistingScenario(tfp, scenarioContent, wrappingFeature);
    }
}
