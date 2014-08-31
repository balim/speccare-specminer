package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.domain.Result;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;

import java.util.Map;

public class ResultStub implements Result {

    private ResultStatus returnedStatus = null;
    private Map<String,ResultStatus> returnedStatusForScenarioName = null;

    private ResultStub() {
    }


    public static Result buildReturningStatus(ResultStatus returnedStatus) {
        ResultStub result = new ResultStub();
        result.returnedStatus = returnedStatus;
        return result;
    }

    public static Result buildReturningStatusForScenarioName(Map<String,ResultStatus> returnedStatusForScenarioName) {
        ResultStub result = new ResultStub();
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
