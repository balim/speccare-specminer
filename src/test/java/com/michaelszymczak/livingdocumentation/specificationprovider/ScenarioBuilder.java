package com.michaelszymczak.livingdocumentation.specificationprovider;


import java.util.Arrays;
import java.util.List;

public class ScenarioBuilder {
    private TextFragmentProvider tfp = new TextFragmentProvider();
    private List<String> scenarioContent = Arrays.asList("Scenario: Default scenario");
    private Feature wrappingFeature = new Feature(tfp, "/default/path/Feature.feature", Arrays.asList("Feature: Default feature"));

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

    public ScenarioBuilder withWrappingFeature(Feature wrappingFeature) {
        this.wrappingFeature = wrappingFeature;
        return this;
    }

    public Scenario build() {
        return new Scenario(tfp, scenarioContent, wrappingFeature);
    }
}
