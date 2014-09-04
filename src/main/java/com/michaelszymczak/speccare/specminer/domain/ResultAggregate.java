package com.michaelszymczak.speccare.specminer.domain;

import java.util.ArrayList;
import java.util.List;

public class ResultAggregate {

    private final List<ResultStatus> results = new ArrayList<>();

    public void add(ResultStatus result) {
        if (ResultStatus.NOT_FOUND != result) {
            results.add(result);
        }
    }

    public ResultStatus result() {
        if (results.isEmpty()) {
            return ResultStatus.NOT_FOUND;
        }
        if (results.size() > 1) {
            return ResultStatus.AMBIGUOUS;
        }

        return results.get(0);
    }

}
