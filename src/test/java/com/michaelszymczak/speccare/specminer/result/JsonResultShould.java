package com.michaelszymczak.speccare.specminer.result;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JsonResultShould {

    @Test public void
    informAboutPassingScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.PASSED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailingScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.FAILED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutNotFoundScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.NOT_FOUND, jsonResult.getResult("Scenario B"));
    }

    @Test public void
    informAboutIgnoredScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.IGNORED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutSkippedScenario() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"skipped\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.SKIPPED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutScenarioThatMatchesTheName() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"},{\"name\": \"Scenario B\",\"steps\": [{\"result\": {\"status\": \"passed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.PASSED, jsonResult.getResult("Scenario B"));
    }

    @Test public void
    informAboutFailingScenarioWhenAtLeastOneFailingStep() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"failed\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.FAILED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutIgnoredScenarioWhenAtLeastOneIgnoredStep() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"passed\"}},{\"result\": {\"status\": \"ignored\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.IGNORED, jsonResult.getResult("Scenario A"));
    }

    @Test public void
    informAboutFailingScenarioInCaseOfSkippedStepsAfterFailedStep() throws IOException {
        JsonResult jsonResult = new JsonResult("[{\"elements\": [{\"name\": \"Scenario A\",\"steps\": [{\"result\": {\"status\": \"failed\"}},{\"result\": {\"status\": \"skipped\"}}],\"type\": \"scenario\"}]}]");
        Assert.assertEquals(JsonResult.result.FAILED, jsonResult.getResult("Scenario A"));
    }
}