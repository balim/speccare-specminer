package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.specificationprovider.PartialResultStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScenarioFinalResultForFoundScenarioShould {

    private static final String SCENARIO_NAME = "My scenario";
    private Scenario foundScenario;

    @Test public void createFinalResponseStatusBasedOnResultDeterminedAfterRunningScenario() throws IOException {
        ScenarioFinalResult scenarioFinalResult = new ScenarioFinalResult(resultAfterRunningScenario(SCENARIO_NAME, ResultStatus.AMBIGUOUS));
        Assert.assertEquals(ResultStatus.AMBIGUOUS, scenarioFinalResult.createResponse(foundScenario).getStatus());
    }

    @Test public void createFinalResponseStatusBasedOnResultDeterminedAfterRunningScenario2() throws IOException {
        ScenarioFinalResult scenarioFinalResult = new ScenarioFinalResult(resultAfterRunningScenario(SCENARIO_NAME, ResultStatus.FAILED));
        Assert.assertEquals(ResultStatus.FAILED, scenarioFinalResult.createResponse(foundScenario).getStatus());
    }

    private PartialResult resultAfterRunningScenario(String scenarioName, ResultStatus status) {
        Map<String, ResultStatus> returnedResult = new HashMap<>();
        returnedResult.put(scenarioName, status);
        return PartialResultStub.buildReturningStatusForScenarioName(returnedResult);
    }



    @Before public void setUp() {
        foundScenario = ScenarioStub.use().withName(SCENARIO_NAME).withResult(ResultStatus.FOUND).build();
    }




}
