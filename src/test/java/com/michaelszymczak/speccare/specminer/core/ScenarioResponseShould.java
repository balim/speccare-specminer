package com.michaelszymczak.speccare.specminer.core;

import com.eclipsesource.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class ScenarioResponseShould {

    @Test public void
    returnContentUsingScenarioData() {
        Scenario scenario = ScenarioBuilder.use()
                .withName("Foo scenario name")
                .withContent("Scenario: Foo scenario name")
                .withResult(ResultStatus.FOUND)
                .withWrappingFeature(FeatureBuilder.use()
                        .withPath("/path/to/foo.feature").build()
                ).build();

        ScenarioResponse response = new ScenarioResponse(scenario);

        Assert.assertEquals(
                "{\"name\":\"Foo scenario name\",\"path\":\"/path/to/foo.feature\",\"content\":[\"Scenario: Foo scenario name\"],\"result\":\"found\"}",
                response.getContent()
        );
    }

    @Test public void
    returnOKHttpStatusForPositiveResults() {
        assertHttpStatusForScenarioStatus(HttpStatus.OK, PASSED);
        assertHttpStatusForScenarioStatus(HttpStatus.OK, FOUND);
    }

    @Test public void
    returnOKHttpStatusForResponsesThatValidatesDocumentationEvenIfTheValidationIsNegative() {
        assertHttpStatusForScenarioStatus(HttpStatus.OK, IGNORED);
        assertHttpStatusForScenarioStatus(HttpStatus.OK, SKIPPED);
        assertHttpStatusForScenarioStatus(HttpStatus.OK, FAILED);
    }

    @Test public void
    returnNotFoundHttpStatusForNotFoundResults() {
        assertHttpStatusForScenarioStatus(HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    @Test public void
    returnUnprocessableEntityHttpStatusWhenProblemWithScenarioOccurred() {
        assertHttpStatusForScenarioStatus(HttpStatus.UNPROCESSABLE_ENTITY, AMBIGUOUS);
        assertHttpStatusForScenarioStatus(HttpStatus.UNPROCESSABLE_ENTITY, UNKNOWN);
    }

    @Test public void
    provideEasyAccessToTheNonHttpResultStatus() {
        Assert.assertEquals(SKIPPED, responseWithScenarioStatus(SKIPPED).getStatus());
    }

    private void assertHttpStatusForScenarioStatus(HttpStatus expectedHttpStatus, ResultStatus originalResponseStatus) {
        Assert.assertEquals(expectedHttpStatus, responseWithScenarioStatus(originalResponseStatus).getHttpStatus());
    }

    private ScenarioResponse responseWithScenarioStatus(ResultStatus status) {
        return new ScenarioResponse(ScenarioBuilder.use().withResult(status).build());
    }

    private void assertResult(ResultStatus expectedResult, ScenarioResponse response) {
        Assert.assertEquals(expectedResult.toString(), JsonObject.readFrom(response.getContent()).get("result").asString());
    }

}
