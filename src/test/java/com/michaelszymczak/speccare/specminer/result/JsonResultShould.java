package com.michaelszymczak.speccare.specminer.result;


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
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.PASSED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailedScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.FAILED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutNotFoundScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.NOT_FOUND, jsonResult.getResult("Scenario B"));
    }

    @Test public void
    informAboutIgnoredScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.IGNORED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutUnknownScenarioIfResultStatusNotRecognized() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"foo\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.UNKNOWN, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    ignoreTypesOtherThanScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"background\"}]}]");
        Assert.assertEquals(JsonResult.Result.NOT_FOUND, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutSkippedScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"skipped\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.SKIPPED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informOnlyAboutScenarioThatMatchesTheName() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario B\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.PASSED, jsonResult.getResult("Scenario B"));
    }

    @Test public void
    informAboutFailingScenarioWhenAtLeastOneFailingStep() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.FAILED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutIgnoredScenarioWhenAtLeastOneIgnoredStep() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.IGNORED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailingScenarioInCaseOfSkippedStepsAfterFailedStep() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}},{\"result\": {\"status\": \"skipped\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.FAILED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutAmbiguousResultIfTooManyScenariosOfGivenNameFound() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.Result.AMBIGUOUS, jsonResult.getResult("Scenario A"));
    }

    private StringReader readerWithValidJsonString() {
        return new StringReader(validJsonString());
    }

    private String validJsonString() {
        return "[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]";
    }
}