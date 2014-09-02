package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.domain.PartialResult;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;

import java.util.Map;

public class PartialResultStub implements PartialResult {

    private ResultStatus returnedStatus = null;
    private Map<String,ResultStatus> returnedStatusForScenarioName = null;

    private PartialResultStub() {
    }


    public static PartialResult buildReturningStatus(ResultStatus returnedStatus) {
        PartialResultStub result = new PartialResultStub();
        result.returnedStatus = returnedStatus;
        return result;
    }

    public static PartialResult buildReturningStatusForScenarioName(Map<String,ResultStatus> returnedStatusForScenarioName) {
        PartialResultStub result = new PartialResultStub();
        result.returnedStatusForScenarioName = returnedStatusForScenarioName;
        return result;
    }

    @Override
    public ResultStatus getResult(String scenarioName) {
        if (returnedStatusForScenarioName.containsKey(scenarioName)) {
            return returnedStatusForScenarioName.get(scenarioName);
        }
        return returnedStatus;
    }


}
