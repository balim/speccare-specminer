package com.michaelszymczak.speccare.specminer.core;

import java.util.ArrayList;
import java.util.List;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class ResultAggregate {

    private final List<ResultStatus> results = new ArrayList<>();

    public void add(ResultStatus result) {
        if (NOT_FOUND != result) {
            results.add(result);
        }
    }

    public ResultStatus result() {
        return (results.isEmpty()) ? NOT_FOUND : pickResult();
    }

    private ResultStatus pickResult() {
        ResultStatus firstResult = results.get(0);
        for (ResultStatus result : results) {
            if (!result.equals(firstResult)) {
                return AMBIGUOUS;
            }
        }
        return firstResult;
    }

}
