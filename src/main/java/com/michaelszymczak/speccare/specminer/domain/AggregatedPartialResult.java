package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.jsonobject.DeterminableCucumberJsonReport;

import java.io.IOException;
import java.io.Reader;

public class AggregatedPartialResult {
    private final ResultSource source;
    private final DeterminableCucumberJsonReport determinable;

    public AggregatedPartialResult(ResultSource source) {
        determinable = new DeterminableCucumberJsonReport();
        this.source = source;
    }

    public Scenario determine(Scenario inputScenario) throws IOException {
        return Scenario.copyWithNewResult(inputScenario, getResult(inputScenario.getName()));
    }

    protected ResultStatus getResult(String scenarioName) throws IOException {
        ResultAggregate aggregate = new ResultAggregate();
        for (Reader resultSource : source.getSources()) {
            aggregate.add(determinable.getResult(resultSource, scenarioName));
        }
        return aggregate.result();
    }
}
