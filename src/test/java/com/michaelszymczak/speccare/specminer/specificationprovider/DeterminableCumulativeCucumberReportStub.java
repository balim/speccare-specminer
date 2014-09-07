package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.*;
import com.michaelszymczak.speccare.specminer.cucumber.DeterminableCumulativeCucumberReport;

import java.io.Reader;
import java.util.List;
import java.util.Map;

public class DeterminableCumulativeCucumberReportStub extends DeterminableCumulativeCucumberReport {

    private ResultStatus returnedStatus = null;
    private Map<String,ResultStatus> returnedStatusForScenarioName = null;

    public DeterminableCumulativeCucumberReportStub() {
        super(new ResultSource() {
            @Override
            public List<Reader> getSources() throws SourceNotFound {
                return null;
            }
        });
    }

    public static DeterminableCumulativeCucumberReportStub buildReturningStatus(ResultStatus returnedStatus) {
        DeterminableCumulativeCucumberReportStub result = new DeterminableCumulativeCucumberReportStub();
        result.returnedStatus = returnedStatus;
        return result;
    }

    public static DeterminableCumulativeCucumberReportStub buildReturningStatusForScenarioName(Map<String,ResultStatus> returnedStatusForScenarioName) {
        DeterminableCumulativeCucumberReportStub result = new DeterminableCumulativeCucumberReportStub();
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
