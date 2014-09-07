package com.michaelszymczak.speccare.specminer.core;

import com.michaelszymczak.speccare.specminer.cucumber.DeterminableCumulativeCucumberReport;

import java.io.IOException;

public class ScenarioFinalResult {

    private final Determinable repository;
    private final DeterminableCumulativeCucumberReport resultAfterExaminingScenarios;

    public ScenarioFinalResult(Determinable repository, DeterminableCumulativeCucumberReport resultAfterExaminingScenarios) {
        this.repository = repository;
        this.resultAfterExaminingScenarios = resultAfterExaminingScenarios;
    }

    public ScenarioResponse createResponse(String scenarioNameFragment) throws IOException {
        Scenario scenario = repository.determine(new SoughtScenario(scenarioNameFragment));
        if (ResultStatus.FOUND == scenario.getResult()) {
            scenario = resultAfterExaminingScenarios.determine(scenario);
        }
        return new ScenarioResponse(scenario);

    }

}
