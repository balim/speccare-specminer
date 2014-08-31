package com.michaelszymczak.speccare.specminer.domain;

import org.junit.Assert;
import org.junit.Test;

public class ScenarioFinalResultShould {

    @Test public void createScenarioResponse() {
        ScenarioFinalResult report = new ScenarioFinalResult();
        Assert.assertNotNull(report.createResponse(scenario()));
    }

//    @Test public void createScenarioResponse() {
//
//    }


    private Scenario scenario() {
        return Scenario.getNotFound();
    }




}
