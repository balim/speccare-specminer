package com.michaelszymczak.speccare.specminer.core;

import java.io.IOException;

public class AllwaysSameResultDeterminableStub implements Determinable {

    private ResultStatus result;

    public AllwaysSameResultDeterminableStub(ResultStatus result) {
        this.result = result;
    }
    @Override
    public Scenario determine(Scenario soughtScenario) throws IOException {
        return Scenario.copyWithNewResult(soughtScenario, result);
    }
}
