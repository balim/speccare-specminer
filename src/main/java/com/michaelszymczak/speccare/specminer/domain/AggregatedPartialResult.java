package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.jsonobject.DeterminableCucumberJsonReport;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AggregatedPartialResult {
    private final ResultSource source;
    private final DeterminableCucumberJsonReport determinable;

    public AggregatedPartialResult(ResultSource source) {
        determinable = new DeterminableCucumberJsonReport();
        this.source = source;
    }

    public ResultStatus getResult(String scenarioName) throws IOException {
        List<ResultStatus> foundStatuses = getFoundStatuses(scenarioName);
        if (foundStatuses.isEmpty()) {
            return ResultStatus.NOT_FOUND;
        }
        if (foundStatuses.size() > 1) {
            return getResultStatusForMoreThanOneSource(foundStatuses);
        }
        return foundStatuses.get(0);
    }

    private List<ResultStatus> getFoundStatuses(String scenarioName) throws IOException {
        List<ResultStatus> foundStatuses = new ArrayList<>();
        for (Reader resultSource : source.getSources()) {
            ResultStatus scenarioStatus = determinable.getResult(resultSource, scenarioName);
            if (ResultStatus.NOT_FOUND != scenarioStatus) {
                foundStatuses.add(scenarioStatus);
            }
        }
        return foundStatuses;
    }

    private ResultStatus getResultStatusForMoreThanOneSource(List<ResultStatus> foundStatuses) {
        ResultStatus lastStatus = null;
        for (ResultStatus each : foundStatuses) {
            if (null != lastStatus && lastStatus != each) {
                return ResultStatus.AMBIGUOUS;
            }
            lastStatus = each;
        }
        return lastStatus;
    }
}
