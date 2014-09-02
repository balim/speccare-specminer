package com.michaelszymczak.speccare.specminer.domain;

import java.io.IOException;

public class ScenarioFinalResult {
    private final PartialResult resultAfterRunningScenarios;
    public ScenarioFinalResult(PartialResult resultAfterRunningScenarios) {
        this.resultAfterRunningScenarios = resultAfterRunningScenarios;
    }

    public ScenarioResponse createResponse(Scenario scenario) throws IOException {
        if (ResultStatus.FOUND != scenario.getResult()) {
            return new ScenarioResponse(scenario);
        }
        ResultStatus resultStatusAfterRunningScenarios = resultAfterRunningScenarios.getResult(scenario.getName());
        return new ScenarioResponse(scenario, resultStatusAfterRunningScenarios);
    }
}
