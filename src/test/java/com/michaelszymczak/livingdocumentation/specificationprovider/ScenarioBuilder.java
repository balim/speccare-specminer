package com.michaelszymczak.livingdocumentation.specificationprovider;


import java.util.Arrays;
import java.util.List;

public class ScenarioBuilder {
    private TextFragmentProvider tfp = new TextFragmentProvider();
    private List<String> scenarioContent = Arrays.asList("Scenario: Default scenario");
    private ExistingFeature wrappingFeature = FeatureBuilder.use().build();

    public static ScenarioBuilder use()
    {
        return new ScenarioBuilder();
    }

    public ScenarioBuilder withTextFragmentProvider(TextFragmentProvider tfp) {
        this.tfp = tfp;
        return this;
    }

    public ScenarioBuilder withContent(String... lines) {
        this.scenarioContent = Arrays.asList(lines);
        return this;
    }

    public ScenarioBuilder withWrappingFeature(ExistingFeature wrappingFeature) {
        this.wrappingFeature = wrappingFeature;
        return this;
    }

    public Scenario build() {
        return new ExistingScenario(tfp, scenarioContent, wrappingFeature);
    }
}
