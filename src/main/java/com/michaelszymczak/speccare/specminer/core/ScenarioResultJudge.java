package com.michaelszymczak.speccare.specminer.core;

import java.io.IOException;

public class ScenarioResultJudge {

    private final Determinable determinableByPhraseFinder;
    private final Determinable determinableByResults;

    public ScenarioResultJudge(Determinable determinableByPhraseFinder, Determinable determinableByResults) {
        this.determinableByPhraseFinder = determinableByPhraseFinder;
        this.determinableByResults = determinableByResults;
    }

    public ScenarioResponse createResponse(String scenarioNameFragment) throws IOException {
        Scenario scenario = determinableByPhraseFinder.determine(new SoughtScenario(scenarioNameFragment));
        if (ResultStatus.FOUND == scenario.getResult()) {
            scenario = determinableByResults.determine(scenario);
        }
        return new ScenarioResponse(scenario);

    }

}
