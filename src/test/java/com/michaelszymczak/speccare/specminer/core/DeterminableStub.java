package com.michaelszymczak.speccare.specminer.core;

import java.io.IOException;
import java.util.Map;

public class DeterminableStub implements Determinable {

    private ResultStatus returnedStatus = null;
    private Map<String,ResultStatus> returnedStatusForScenarioName = null;

    public static DeterminableStub buildReturningStatus(ResultStatus returnedStatus) {
        DeterminableStub result = new DeterminableStub();
        result.returnedStatus = returnedStatus;
        return result;
    }

    public static DeterminableStub buildReturningStatusForScenarioName(Map<String,ResultStatus> returnedStatusForScenarioName) {
        DeterminableStub result = new DeterminableStub();
        result.returnedStatusForScenarioName = returnedStatusForScenarioName;
        return result;
    }

    @Override
    public Scenario determine(Scenario soughtScenario) throws IOException {
        return Scenario.copyWithNewResult(soughtScenario, getResult(soughtScenario.getName()));
    }

    private ResultStatus getResult(String scenarioName) {
        if (null != returnedStatusForScenarioName) {
            return returnedStatusForScenarioName.get(scenarioName);
        }
        return returnedStatus;
    }
}
