package com.michaelszymczak.speccare.specminer.featurefiles;


import com.michaelszymczak.speccare.specminer.core.FeatureBuilder;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.core.ScenarioBuilder;

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
        return ScenarioBuilder.use().withWrappingFeature(FeatureBuilder.use().withPath(foo).build()).build();
    }

}
