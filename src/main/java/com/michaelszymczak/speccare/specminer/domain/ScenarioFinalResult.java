package com.michaelszymczak.speccare.specminer.domain;

import java.io.IOException;

public class ScenarioFinalResult {

    private final ScenarioRepository repository;
    private final PartialResult resultAfterExaminingScenarios;

    public ScenarioFinalResult(ScenarioRepository repository, PartialResult resultAfterExaminingScenarios) {
        this.repository = repository;
        this.resultAfterExaminingScenarios = resultAfterExaminingScenarios;
    }

    public ScenarioResponse createResponse(String scenarioNameFragment) throws IOException {
        Scenario scenario = repository.find(scenarioNameFragment);
        if (ResultStatus.FOUND != scenario.getResult()) {
            return new ScenarioResponse(scenario);
        }
        ResultStatus resultStatusAfterRunningScenarios = resultAfterExaminingScenarios.getResult(scenario.getName());
        return new ScenarioResponse(scenario, resultStatusAfterRunningScenarios);
    }

}
