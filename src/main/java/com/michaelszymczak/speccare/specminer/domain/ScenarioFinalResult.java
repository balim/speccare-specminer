package com.michaelszymczak.speccare.specminer.domain;

import java.io.IOException;

public class ScenarioFinalResult {

    private final ScenarioRepository repository;
    private final AggregatedPartialResult resultAfterExaminingScenarios;

    public ScenarioFinalResult(ScenarioRepository repository, AggregatedPartialResult resultAfterExaminingScenarios) {
        this.repository = repository;
        this.resultAfterExaminingScenarios = resultAfterExaminingScenarios;
    }

    public ScenarioResponse createResponse(String scenarioNameFragment) throws IOException {
        Scenario scenario = repository.find(scenarioNameFragment);
        if (ResultStatus.FOUND == scenario.getResult()) {
            scenario = resultAfterExaminingScenarios.determine(scenario);
        }
        return new ScenarioResponse(scenario);

    }

}
