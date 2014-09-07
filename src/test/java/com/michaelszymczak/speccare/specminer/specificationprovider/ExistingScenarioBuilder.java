package com.michaelszymczak.speccare.specminer.specificationprovider;


import com.michaelszymczak.speccare.specminer.core.ExistingFeature;
import com.michaelszymczak.speccare.specminer.core.ExistingScenario;
import com.michaelszymczak.speccare.specminer.core.ResultStatus;

import java.util.Arrays;
import java.util.List;

public class ExistingScenarioBuilder {
    private final TextFragmentProvider tfp = new TextFragmentProvider();
    private List<String> scenarioContent = Arrays.asList("Scenario: Default scenario");
    private ExistingFeature wrappingFeature = ExistingFeatureBuilder.use().build();
    private ResultStatus result;

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

    public ExistingScenarioBuilder withResult(ResultStatus result) {
        this.result = result;
        return this;
    }

    public ExistingScenario build() {
        return new ExistingScenario(tfp, scenarioContent, wrappingFeature);
    }
}
