package com.michaelszymczak.speccare.specminer.domain;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class JsonResultShould {

    @Test public void
    canBeCreatedUsingReader() throws IOException {
        new JsonResult(readerWithValidJsonString());
    }

    @Test public void
    canBeCreatedUsingString() throws IOException {
        new JsonResult(validJsonString());
    }

    @Test public void
    informAboutPassedScenario() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.PASSED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailedScenario() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.FAILED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutNotFoundScenario() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.NOT_FOUND, result.getResult("Scenario B"));
    }

    @Test public void
    informAboutIgnoredScenario() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.IGNORED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioIfResultStatusNotRecognized() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"foo\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.UNKNOWN, result.getResult("Scenario A"));
    }

    @Test public void
    ignoreTypesOtherThanScenario() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"background\"}]}]");
        Assert.assertEquals(ResultStatus.NOT_FOUND, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutSkippedScenario() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"skipped\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.SKIPPED, result.getResult("Scenario A"));
    }

    @Test public void
    informOnlyAboutScenarioThatMatchesTheName() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario B\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.PASSED, result.getResult("Scenario B"));
    }

    @Test public void
    informAboutFailingScenarioWhenAtLeastOneFailingStep() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.FAILED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutIgnoredScenarioWhenAtLeastOneIgnoredStep() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.IGNORED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailingScenarioInCaseOfSkippedStepsAfterFailedStep() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}},{\"result\": {\"status\": \"skipped\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.FAILED, result.getResult("Scenario A"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyScenariosOfGivenNameFound() throws IOException {
        Result result = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(ResultStatus.AMBIGUOUS, result.getResult("Scenario A"));
    }

    private StringReader readerWithValidJsonString() {
        return new StringReader(validJsonString());
    }

    private String validJsonString() {
        return "[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]";
    }
}