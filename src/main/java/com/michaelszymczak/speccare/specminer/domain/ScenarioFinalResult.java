package com.michaelszymczak.speccare.specminer.domain;

public class ScenarioFinalResult {
    private final Result resultAfterRunningScenarios;
    public ScenarioFinalResult(Result resultAfterRunningScenarios) {
        this.resultAfterRunningScenarios = resultAfterRunningScenarios;
    }

    public ScenarioResponse createResponse(Scenario scenario) {
        if (ResultStatus.FOUND != scenario.getResult()) {
            return new ScenarioResponse(scenario);
        }
        ResultStatus resultStatusAfterRunningScenarios = resultAfterRunningScenarios.getResult(scenario.getName());
        return new ScenarioResponse(scenario, resultStatusAfterRunningScenarios);
    }
}
