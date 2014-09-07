package com.michaelszymczak.speccare.specminer.core;


import com.michaelszymczak.speccare.specminer.specificationprovider.ExistingFeatureBuilder;
import com.michaelszymczak.speccare.specminer.specificationprovider.ExistingScenarioBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScenarioShould {
    @Test public void
    createCopyOfItselfUpdatingTheResult() {
        Scenario origin = createScenarioWithStatus(ResultStatus.FOUND);
        Scenario copy = Scenario.copyWithNewResult(origin, ResultStatus.PASSED);
        assertEqualsOriginWithNewStatus(origin, ResultStatus.PASSED, copy);
    }

    private void assertEqualsOriginWithNewStatus(Scenario origin, ResultStatus expectedStatus, Scenario copy) {
        assertEquals(expectedStatus, copy.getResult());
        assertEquals(origin.getName(), copy.getName());
        assertEquals(origin.getContent(), copy.getContent());
        assertEquals(origin.getFeature(), copy.getFeature());
        assertEquals(origin.getFeaturePath(), copy.getFeaturePath());
    }

    private Scenario createScenarioWithStatus(ResultStatus result) {
        return ExistingScenarioBuilder.use()
                    .withContent("Scenario: Foo")
                    .withResult(result)
                    .withWrappingFeature(ExistingFeatureBuilder.use()
                                    .withPath("/bar/foo.feature")
                                    .withContent("Feature: Bar", "Scenario: Foo")
                                    .build()
                    ).build();
    }
}
