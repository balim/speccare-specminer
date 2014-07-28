package com.michaelszymczak.livingdocumentation.specificationprovider;


import java.util.ArrayList;
import java.util.List;

public class AmbiguousScenarioBuilder {
    private List<String> featurePaths = new ArrayList<>();

    public static AmbiguousScenarioBuilder use()
    {
        return new AmbiguousScenarioBuilder();
    }

    public AmbiguousScenarioBuilder addFeaturePath(String path) {
        featurePaths.add(path);
        return this;
    }

    public AmbiguousScenario build() {
        List<Scenario> existingScenarios = new ArrayList<>();
        for (String path : featurePaths) {
            existingScenarios.add(scenarioWithFeaturePath(path));
        }
        return new AmbiguousScenario(existingScenarios);
    }

    private Scenario scenarioWithFeaturePath(String foo) {
        return ExistingScenarioBuilder.use().withWrappingFeature(ExistingFeatureBuilder.use().withPath(foo).build()).build();
    }

}
