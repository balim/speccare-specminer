package com.michaelszymczak.speccare.specminer.jsonobject;


import com.michaelszymczak.speccare.specminer.domain.JsonResultString;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class DeterminableCucumberJsonReportShould {

    @Test public void
    informAboutPassedScenario() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.PASSED).asReader();
        Assert.assertEquals(ResultStatus.PASSED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutFailedScenario() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.FAILED).asReader();
        Assert.assertEquals(ResultStatus.FAILED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutNotFoundScenario() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.PASSED).asReader();
        Assert.assertEquals(ResultStatus.NOT_FOUND, result.getResult(json, "Scenario B"));
    }

    @Test public void
    informAboutIgnoredScenario() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.IGNORED).asReader();
        Assert.assertEquals(ResultStatus.IGNORED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioResultIfResultStatusNotRecognized() throws IOException {
        String json = "[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"foo\"}}],\"type\": \"scenario\"}]}]";
        Assert.assertEquals(ResultStatus.UNKNOWN, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioResultIfNoResultWhatsoever() throws IOException {
        Reader json = json().name("Scenario A").noResult().asReader();
        Assert.assertEquals(ResultStatus.UNKNOWN, result.getResult(json, "Scenario A"));
    }

    @Test public void
    ignoreTypesOtherThanScenario() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.PASSED).type("background").asReader();
        Assert.assertEquals(ResultStatus.NOT_FOUND, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutSkippedScenario() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.SKIPPED).asReader();
        Assert.assertEquals(ResultStatus.SKIPPED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informOnlyAboutScenarioThatMatchesTheName() throws IOException {
        String json = "[{\"elements\": [" + json().name("Scenario A").result(ResultStatus.FAILED).asOneScenarioString() + ","
                + json().name("Scenario B").result(ResultStatus.PASSED).asOneScenarioString() + "]}]";

        Assert.assertEquals(ResultStatus.PASSED, result.getResult(json, "Scenario B"));
    }

    @Test public void
    informAboutFailingScenarioWhenAtLeastOneFailingStep() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.PASSED).nextResult(ResultStatus.FAILED).asReader();
        Assert.assertEquals(ResultStatus.FAILED, result.getResult(json, "Scenario A"));

    }

    @Test public void
    informAboutIgnoredScenarioWhenAtLeastOneIgnoredStep() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.PASSED).nextResult(ResultStatus.IGNORED).asReader();
        Assert.assertEquals(ResultStatus.IGNORED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutFailingScenarioInCaseOfSkippedStepsAfterFailedStep() throws IOException {
        Reader json = json().name("Scenario A").result(ResultStatus.FAILED).nextResult(ResultStatus.SKIPPED).asReader();
        Assert.assertEquals(ResultStatus.FAILED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyScenariosOfGivenNameFound() throws IOException {
        String json = "[{\"elements\": [" + json().name("Scenario A").asOneScenarioString() + ","
                + json().name("Scenario A").asOneScenarioString() + "]}]";

        Assert.assertEquals(ResultStatus.AMBIGUOUS, result.getResult(json, "Scenario A"));
    }

    @Test public void
    searchAllEntries() throws IOException {
        String json = "[" + json().name("Scenario A").asOneEntryString() + ", "
                + json().name("Scenario B").asOneEntryString() + "]";

        Assert.assertNotEquals(ResultStatus.NOT_FOUND, result.getResult(json, "Scenario B"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyEntitiesWithScenariosOfGivenNameFound() throws IOException {
        String json = "[" + json().name("Scenario A").asOneEntryString() + ", "
                + json().name("Scenario A").asOneEntryString() + "]";

        Assert.assertEquals(ResultStatus.AMBIGUOUS, result.getResult(json, "Scenario A"));
    }

    private DeterminableCucumberJsonReport result;

    @Before public void setUp() {
        result = new DeterminableCucumberJsonReport();
    }

    private JsonResultString.Builder json() {
        return new JsonResultString.Builder();
    }
}