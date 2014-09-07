package com.michaelszymczak.speccare.specminer.cucumber;

import com.michaelszymczak.speccare.specminer.core.*;

import java.io.IOException;
import java.io.Reader;

public class DeterminableCumulativeCucumberReport implements Determinable {
    private final ResultSource source;
    private final ResultKnowingCucumberJsonReport resultKnowing;

    public DeterminableCumulativeCucumberReport(ResultSource source) {
        resultKnowing = new ResultKnowingCucumberJsonReport();
        this.source = source;
    }

    public Scenario determine(Scenario inputScenario) throws IOException {
        return Scenario.copyWithNewResult(inputScenario, getResult(inputScenario.getName()));
    }

    protected ResultStatus getResult(String scenarioName) throws IOException {
        ResultAggregate aggregate = new ResultAggregate();
        for (Reader resultSource : source.getSources()) {
            aggregate.add(resultKnowing.getResult(resultSource, scenarioName));
        }
        return aggregate.result();
    }
}
