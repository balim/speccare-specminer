package com.michaelszymczak.speccare.specminer.core;


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
        return ScenarioBuilder.use()
                .withContent("Scenario: Foo")
                .withName("Scenario: Foo")
                .withWrappingFeature(FeatureBuilder.use()
                            .withPath("/bar/foo.feature")
                            .withName("Bar")
                            .withContent("Feature: Bar", "Scenario: Foo")
                            .build()
                ).build();
    }
}
