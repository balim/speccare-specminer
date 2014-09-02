package com.michaelszymczak.speccare.specminer.domain;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class JsonPartialResultShould {

    @Test public void
    canBeCreatedUsingReader() throws IOException {
        new JsonPartialResult(readerWithValidJsonString());
    }

    @Test public void
    canBeCreatedUsingString() throws IOException {
        new JsonPartialResult(validJsonString());
    }

    @Test public void
    informAboutPassedScenario() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.PASSED));
        Assert.assertEquals(ResultStatus.PASSED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailedScenario() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.FAILED));
        Assert.assertEquals(ResultStatus.FAILED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutNotFoundScenario() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.PASSED));
        Assert.assertEquals(ResultStatus.NOT_FOUND, result.getResult("Scenario B"));
    }

    @Test public void
    informAboutIgnoredScenario() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.IGNORED));
        Assert.assertEquals(ResultStatus.IGNORED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioIfResultStatusNotRecognized() throws IOException {
        PartialResult result = new JsonPartialResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"foo\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.UNKNOWN, result.getResult("Scenario A"));
    }

    @Test public void
    ignoreTypesOtherThanScenario() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.PASSED).type("background"));
        Assert.assertEquals(ResultStatus.NOT_FOUND, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutSkippedScenario() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.SKIPPED));
        Assert.assertEquals(ResultStatus.SKIPPED, result.getResult("Scenario A"));
    }

    @Test public void
    informOnlyAboutScenarioThatMatchesTheName() throws IOException {
        PartialResult result = new JsonPartialResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario B\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.PASSED, result.getResult("Scenario B"));
    }

    @Test public void
    informAboutFailingScenarioWhenAtLeastOneFailingStep() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.PASSED).nextResult(ResultStatus.FAILED));
        Assert.assertEquals(ResultStatus.FAILED, result.getResult("Scenario A"));

    }

    @Test public void
    informAboutIgnoredScenarioWhenAtLeastOneIgnoredStep() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.PASSED).nextResult(ResultStatus.IGNORED));
        Assert.assertEquals(ResultStatus.IGNORED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailingScenarioInCaseOfSkippedStepsAfterFailedStep() throws IOException {
        PartialResult result = create(json().name("Scenario A").result(ResultStatus.FAILED).nextResult(ResultStatus.SKIPPED));
        Assert.assertEquals(ResultStatus.FAILED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyScenariosOfGivenNameFound() throws IOException {
        PartialResult result = new JsonPartialResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.AMBIGUOUS, result.getResult("Scenario A"));
    }

    private Reader readerWithValidJsonString() {
        return json().asReader();
    }

    private String validJsonString() {
        return json().asString();
    }

    private JsonPartialResult create(JsonResultString.Builder jsonBuilder) throws IOException {
        return new JsonPartialResult(jsonBuilder.asReader());
    }

    private JsonResultString.Builder json() {
        return new JsonResultString.Builder();
    }
}