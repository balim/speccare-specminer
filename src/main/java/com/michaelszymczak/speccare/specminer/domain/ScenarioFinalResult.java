package com.michaelszymczak.speccare.specminer.domain;

public class ScenarioFinalResult {
    public ScenarioResponse createResponse(Scenario scenario) {
        return new ScenarioResponse(Scenario.getNotFound(), ResultStatus.SKIPPED);
    }
}
