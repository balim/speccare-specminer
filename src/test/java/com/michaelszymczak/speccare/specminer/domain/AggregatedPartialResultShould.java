package com.michaelszymczak.speccare.specminer.domain;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AggregatedPartialResultShould {

    @Test public void
    useSourceToGenerateResult() throws IOException {
        ResultSource source = resultSource(json().name("Scenario A").result(ResultStatus.PASSED));
        Assert.assertEquals(ResultStatus.PASSED, new AggregatedPartialResult(source).getResult("Scenario A"));
    }

    @Test public void
    returnNotFoundIfScenarioOfMatchingNameNotPresent() throws IOException {
        ResultSource source = resultSource(json().name("Scenario A").result(ResultStatus.PASSED));
        Assert.assertEquals(ResultStatus.NOT_FOUND, new AggregatedPartialResult(source).getResult("Scenario B"));
    }

    @Test public void
    searchAllSourcesToGenerateResult() throws IOException {
        ResultSource source = resultSource(
                json().name("Scenario A").result(ResultStatus.PASSED),
                json().name("Scenario B").result(ResultStatus.FAILED),
                json().name("Scenario C").result(ResultStatus.SKIPPED)
        );
        Assert.assertEquals(ResultStatus.FAILED, new AggregatedPartialResult(source).getResult("Scenario B"));
    }

    @Test public void
    returnAmbiguousResultIfMoreThanOneStatusFoundForScenarioOfTheSameName() throws IOException {
        ResultSource source = resultSource(
                json().name("Scenario A").result(ResultStatus.SKIPPED),
                json().name("Scenario B").result(ResultStatus.FAILED),
                json().name("Scenario B").result(ResultStatus.PASSED)
        );
        Assert.assertEquals(ResultStatus.AMBIGUOUS, new AggregatedPartialResult(source).getResult("Scenario B"));
    }

    @Test public void
    returnFoundScenarioStatusIfAllScenarioStatusesAreTheSame() throws IOException {
        ResultSource source = resultSource(
                json().name("Scenario A").result(ResultStatus.SKIPPED),
                json().name("Scenario B").result(ResultStatus.FAILED),
                json().name("Scenario B").result(ResultStatus.FAILED)
        );
        Assert.assertEquals(ResultStatus.FAILED, new AggregatedPartialResult(source).getResult("Scenario B"));
    }

    private ResultSource resultSource(final JsonResultString.Builder... jsonBuilder) {
        return new ResultSource() {
            @Override
            public List<Reader> getSources() {
                List<Reader> sources = new ArrayList<>();
                for (JsonResultString.Builder jsonString : jsonBuilder) {
                    sources.add(jsonString.asReader());
                }
                return sources;
            }
        };
    }

    private JsonResultString.Builder json() {
        return new JsonResultString.Builder();
    }
}
