package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.domain.Determinable;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;

import java.util.Map;

public class DeterminableStub implements Determinable {

    private ResultStatus returnedStatus = null;
    private Map<String,ResultStatus> returnedStatusForScenarioName = null;

    private DeterminableStub() {
    }


    public static Determinable buildReturningStatus(ResultStatus returnedStatus) {
        DeterminableStub result = new DeterminableStub();
        result.returnedStatus = returnedStatus;
        return result;
    }

    public static Determinable buildReturningStatusForScenarioName(Map<String,ResultStatus> returnedStatusForScenarioName) {
        DeterminableStub result = new DeterminableStub();
        result.returnedStatusForScenarioName = returnedStatusForScenarioName;
        return result;
    }

    @Override
    public ResultStatus getResult(String scenarioName) {
        if (null != returnedStatusForScenarioName) {
            return returnedStatusForScenarioName.get(scenarioName);
        }
        return returnedStatus;
    }


}
