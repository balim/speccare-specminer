package com.michaelszymczak.speccare.specminer.domain;

import org.junit.Assert;
import org.junit.Test;

public class ScenarioReportShould {

    @Test public void createScenarioResponse() {
        ScenarioReport report = new ScenarioReport();
        Assert.assertNotNull(report.createResponse(scenario()));
    }

//    @Test public void createScenarioResponse() {
//
//    }


    private Scenario scenario() {
        return Scenario.getNotFound();
    }




}
