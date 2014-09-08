package com.michaelszymczak.speccare.specminer.core;


import java.util.Arrays;
import java.util.List;

public class ScenarioBuilder {
    private String name = "Default scenario";
    private List<String> scenarioContent = Arrays.asList("Scenario: Default scenario");
    private Feature wrappingFeature = new Feature() {
        @Override
        public String getName() {
            return "Default feature";
        }

        @Override
        public String getPath() {
            return "/bar/foo.feature";
        }

        @Override
        public List<String> getContent() {
            return Arrays.asList("Feature: Default feature", "  Scenario: Default scenario");
        }
    };
    private ResultStatus result = ResultStatus.UNKNOWN;
    public static ScenarioBuilder use()
    {
        return new ScenarioBuilder();
    }

    public ScenarioBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ScenarioBuilder withContent(String... lines) {
        this.scenarioContent = Arrays.asList(lines);
        return this;
    }

    public ScenarioBuilder withResult(ResultStatus result) {
        this.result = result;
        return this;
    }

    public ScenarioBuilder withWrappingFeature(Feature wrappingFeature) {
        this.wrappingFeature = wrappingFeature;
        return this;
    }

    public Scenario build() {
        return new BuiltScenario(this);
    }

    private static class BuiltScenario extends Scenario {
        BuiltScenario(ScenarioBuilder builder) {
            super(builder.name, builder.wrappingFeature, builder.scenarioContent, builder.result);
        }
    }
}
