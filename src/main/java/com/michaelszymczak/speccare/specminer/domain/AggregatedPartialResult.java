package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.jsonobject.JsonPartialResult;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AggregatedPartialResult implements Determinable {
    private final ResultSource source;
    public AggregatedPartialResult(ResultSource source) {
        this.source = source;
    }

    @Override
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
            ResultStatus scenarioStatus = new JsonPartialResult(resultSource).getResult(scenarioName);
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
