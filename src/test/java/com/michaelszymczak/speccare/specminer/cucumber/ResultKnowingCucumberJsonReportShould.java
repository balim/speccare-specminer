package com.michaelszymczak.speccare.specminer.cucumber;


import com.michaelszymczak.speccare.specminer.core.JsonResultString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class ResultKnowingCucumberJsonReportShould {

    @Test public void
    informAboutPassedScenario() throws IOException {
        Reader json = json().name("Scenario A").result(PASSED).asReader();
        Assert.assertEquals(PASSED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutFailedScenario() throws IOException {
        Reader json = json().name("Scenario A").result(FAILED).asReader();
        Assert.assertEquals(FAILED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutNotFoundScenario() throws IOException {
        Reader json = json().name("Scenario A").result(PASSED).asReader();
        Assert.assertEquals(NOT_FOUND, result.getResult(json, "Scenario B"));
    }

    @Test public void
    informAboutIgnoredScenario() throws IOException {
        Reader json = json().name("Scenario A").result(IGNORED).asReader();
        Assert.assertEquals(IGNORED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioResultIfResultStatusNotRecognized() throws IOException {
        String json = "[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"foo\"}}],\"type\": \"scenario\"}]}]";
        Assert.assertEquals(UNKNOWN, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioResultIfNoResultWhatsoever() throws IOException {
        Reader json = json().name("Scenario A").noResult().asReader();
        Assert.assertEquals(UNKNOWN, result.getResult(json, "Scenario A"));
    }

    @Test public void
    ignoreTypesOtherThanScenario() throws IOException {
        Reader json = json().name("Scenario A").result(PASSED).type("background").asReader();
        Assert.assertEquals(NOT_FOUND, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutSkippedScenario() throws IOException {
        Reader json = json().name("Scenario A").result(SKIPPED).asReader();
        Assert.assertEquals(SKIPPED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informOnlyAboutScenarioThatMatchesTheName() throws IOException {
        String json = "[{\"elements\": [" + json().name("Scenario A").result(FAILED).asOneScenarioString() + ","
                + json().name("Scenario B").result(PASSED).asOneScenarioString() + "]}]";

        Assert.assertEquals(PASSED, result.getResult(json, "Scenario B"));
    }

    @Test public void
    informAboutFailingScenarioWhenAtLeastOneFailingStep() throws IOException {
        Reader json = json().name("Scenario A").result(PASSED).nextResult(FAILED).asReader();
        Assert.assertEquals(FAILED, result.getResult(json, "Scenario A"));

    }

    @Test public void
    informAboutIgnoredScenarioWhenAtLeastOneIgnoredStep() throws IOException {
        Reader json = json().name("Scenario A").result(PASSED).nextResult(IGNORED).asReader();
        Assert.assertEquals(IGNORED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutFailingScenarioInCaseOfSkippedStepsAfterFailedStep() throws IOException {
        Reader json = json().name("Scenario A").result(FAILED).nextResult(SKIPPED).asReader();
        Assert.assertEquals(FAILED, result.getResult(json, "Scenario A"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyScenariosOfDifferentResultAndGivenNameFound() throws IOException {
        String json = "[{\"elements\": [" + json().name("Scenario A").result(IGNORED).asOneScenarioString() + ","
                + json().name("Scenario A").result(PASSED).asOneScenarioString() + "]}]";

        Assert.assertEquals(AMBIGUOUS, result.getResult(json, "Scenario A"));
    }

    @Test public void
    searchAllEntries() throws IOException {
        String json = "[" + json().name("Scenario A").asOneEntryString() + ", "
                + json().name("Scenario B").asOneEntryString() + "]";

        Assert.assertNotEquals(NOT_FOUND, result.getResult(json, "Scenario B"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyEntitiesWithScenariosOfGivenNameAndDifferentResultFound() throws IOException {
        String json = "[" + json().name("Scenario A").result(FAILED).asOneEntryString() + ", "
                + json().name("Scenario A").result(PASSED).asOneEntryString() + "]";

        Assert.assertEquals(AMBIGUOUS, result.getResult(json, "Scenario A"));
    }

    private ResultKnowingCucumberJsonReport result;

    @Before public void setUp() {
        result = new ResultKnowingCucumberJsonReport();
    }

    private JsonResultString.Builder json() {
        return new JsonResultString.Builder();
    }
}