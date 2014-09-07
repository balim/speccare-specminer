package com.michaelszymczak.speccare.specminer.cucumber;


import com.michaelszymczak.speccare.specminer.core.JsonResultString;
import com.michaelszymczak.speccare.specminer.core.ResultSource;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.core.SoughtScenario;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class DeterminableCumulativeCucumberReportShould {

    @Test public void
    useSourceToGenerateResult() throws IOException {
        ResultSource source = resultSource(json().name("Scenario A").result(PASSED));
        String scenarioName = "Scenario A";
        Assert.assertEquals(PASSED, determine(source, scenarioName).getResult());
    }

    @Test public void
    returnNotFoundIfScenarioOfMatchingNameNotPresent() throws IOException {
        ResultSource source = resultSource(json().name("Scenario A").result(PASSED));
        Assert.assertEquals(NOT_FOUND, determine(source, "Scenario B").getResult());
    }

    @Test public void
    searchAllSourcesToGenerateResult() throws IOException {
        ResultSource source = resultSource(
                json().name("Scenario A").result(PASSED),
                json().name("Scenario B").result(FAILED),
                json().name("Scenario C").result(SKIPPED)
        );
        Assert.assertEquals(FAILED, determine(source, "Scenario B").getResult());
    }

    @Test public void
    returnAmbiguousResultIfMoreThanOneStatusFoundForScenarioOfTheSameName() throws IOException {
        ResultSource source = resultSource(
                json().name("Scenario A").result(SKIPPED),
                json().name("Scenario B").result(FAILED),
                json().name("Scenario B").result(PASSED)
        );
        Assert.assertEquals(AMBIGUOUS, determine(source, "Scenario B").getResult());
    }

    @Test public void
    returnFoundScenarioStatusIfAllScenarioStatusesAreTheSame() throws IOException {
        ResultSource source = resultSource(
                json().name("Scenario A").result(SKIPPED),
                json().name("Scenario B").result(FAILED),
                json().name("Scenario B").result(FAILED)
        );
        Assert.assertEquals(FAILED, determine(source, "Scenario B").getResult());
    }

    private Scenario determine(ResultSource source, String scenarioName) throws IOException {
        Scenario scenario = new SoughtScenario(scenarioName);
        return new DeterminableCumulativeCucumberReport(source).determine(scenario);
//        return new AggregatedPartialResult(source).getResult(scenarioName);
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
