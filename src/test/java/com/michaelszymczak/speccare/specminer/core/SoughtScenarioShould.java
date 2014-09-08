package com.michaelszymczak.speccare.specminer.core;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class SoughtScenarioShould {

    @Test public void
    provideOnlyNameThatCanBeUsedToSeekTheRealScenario() {
        Scenario scenario = new SoughtScenario("Sought fragment");
        assertEquals("Sought fragment", scenario.getName());
    }

    @Test public void
    haveNotFoundFeatureAsItsFeature() {
        assertSame(Feature.getNotFound(), soughtScenario.getFeature());
    }

    @Test public void
    haveNoContent() {
        assertTrue(soughtScenario.getContent().isEmpty());
    }

    @Test public void
    haveUnknownResult() {
        assertEquals(ResultStatus.UNKNOWN, soughtScenario.getResult());
    }

    private Scenario soughtScenario;
    @Before public void setUp() {
        soughtScenario = new SoughtScenario("foo");
    }
}
