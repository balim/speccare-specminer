package com.michaelszymczak.livingdocumentation.specificationprovider;


import com.michaelszymczak.livingdocumentation.domain.AmbiguousScenario;
import com.michaelszymczak.livingdocumentation.domain.Scenario;

import java.util.ArrayList;
import java.util.List;

public class AmbiguousScenarioBuilder {
    private final List<String> featurePaths = new ArrayList<>();

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
