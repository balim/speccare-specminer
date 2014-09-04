package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.domain.*;

import java.io.Reader;
import java.util.List;
import java.util.Map;

public class AggregatedPartialResultStub extends AggregatedPartialResult {

    private ResultStatus returnedStatus = null;
    private Map<String,ResultStatus> returnedStatusForScenarioName = null;

    public AggregatedPartialResultStub() {
        super(new ResultSource() {
            @Override
            public List<Reader> getSources() throws SourceNotFound {
                return null;
            }
        });
    }

    public static AggregatedPartialResultStub buildReturningStatus(ResultStatus returnedStatus) {
        AggregatedPartialResultStub result = new AggregatedPartialResultStub();
        result.returnedStatus = returnedStatus;
        return result;
    }

    public static AggregatedPartialResultStub buildReturningStatusForScenarioName(Map<String,ResultStatus> returnedStatusForScenarioName) {
        AggregatedPartialResultStub result = new AggregatedPartialResultStub();
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
