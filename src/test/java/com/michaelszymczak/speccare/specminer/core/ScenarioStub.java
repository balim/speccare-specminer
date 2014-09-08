package com.michaelszymczak.speccare.specminer.core;


import java.util.Arrays;
import java.util.List;

public class ScenarioStub {
    private List<String> content = Arrays.asList("Scenario: Default scenario");
    private ExistingFeature wrappingFeature = ExistingFeatureBuilder.use().build();
    private ResultStatus result = ResultStatus.UNKNOWN;
    private String name = "Default scenario";

    public static ScenarioStub use()
    {
        return new ScenarioStub();
    }

    public ScenarioStub withContent(String... lines) {
        this.content = Arrays.asList(lines);
        return this;
    }

    public ScenarioStub withWrappingFeature(ExistingFeature wrappingFeature) {
        this.wrappingFeature = wrappingFeature;
        return this;
    }

    public ScenarioStub withName(String name) {
        this.name = name;
        return this;
    }

    public ScenarioStub withResult(ResultStatus result) {
        this.result = result;
        return this;
    }

    public Scenario build() {
        final String scenarioName = this.name;
        final Feature scenarioWrappingFeature = this.wrappingFeature;
        final List<String> scenarioContent = this.content;
        final ResultStatus scenarioResult = this.result;

        return new Scenario() {
            @Override
            public String getName() {
                return scenarioName;
            }

            @Override
            public Feature getFeature() {
                return scenarioWrappingFeature;
            }

            @Override
            public List<String> getContent() {
                return scenarioContent;
            }

            @Override
            public ResultStatus getResult() {
                return scenarioResult;
            }
        };
    }
}
