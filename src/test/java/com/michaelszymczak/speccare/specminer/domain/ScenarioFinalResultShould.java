package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.specificationprovider.ResultStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScenarioFinalResultShould {

    @Test public void createScenarioResponse() {
        Assert.assertNotNull(result.createResponse(scenario()));
    }

    @Test public void useTheScenarioToGenerateTheResponse() {
        Scenario scenario = ScenarioStub.use().withContent("Scenario: Bar").build();
        Assert.assertTrue(result.createResponse(scenario).getContent().contains("\"content\":[\"Scenario: Bar\"]"));
    }

    @Test public void doNotAlterTheFinalResultUnlessOriginalScenarioResultIsFound() {
        assertResponseStatusForScenario(ResultStatus.FAILED, scenarioWithResult(ResultStatus.FAILED));
        assertResponseStatusForScenario(ResultStatus.NOT_FOUND, Scenario.getNotFound());
        assertResponseStatusForScenario(ResultStatus.IGNORED, scenarioWithResult(ResultStatus.IGNORED));
        assertResponseStatusForScenario(ResultStatus.SKIPPED, scenarioWithResult(ResultStatus.SKIPPED));
        assertResponseStatusForScenario(ResultStatus.AMBIGUOUS, scenarioWithResult(ResultStatus.AMBIGUOUS));
        assertResponseStatusForScenario(ResultStatus.UNKNOWN, scenarioWithResult(ResultStatus.UNKNOWN));
        assertResponseStatusForScenario(ResultStatus.PASSED, scenarioWithResult(ResultStatus.PASSED));
    }


    private ScenarioFinalResult result;

    @Before public void setUp() {
        result = new ScenarioFinalResult(ResultStub.buildReturningStatus(ResultStatus.UNKNOWN));
    }

    private void assertResponseStatusForScenario(ResultStatus expectedStatus, Scenario scenario) {
        Assert.assertEquals(expectedStatus, result.createResponse(scenario).getStatus());
    }

    private Scenario scenario() {
        return ScenarioStub.use().build();
    }

    private Scenario scenarioWithResult(final ResultStatus status) {
        return ScenarioStub.use().withResult(status).build();
    }




}
