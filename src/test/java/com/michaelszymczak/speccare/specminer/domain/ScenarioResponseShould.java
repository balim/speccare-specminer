package com.michaelszymczak.speccare.specminer.domain;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.springframework.http.HttpStatus;

public class ScenarioResponseShould {

    @Test public void
    returnContentUsingScenarioData() {
        Scenario scenario = getScenario("Foo scenario name", "Scenario: Foo scenario name", ResultStatus.FOUND, "/path/to/foo.feature");

        ScenarioResponse response = new ScenarioResponse(scenario, ResultStatus.FOUND);

        Assert.assertEquals(
                "{\"name\":\"Foo scenario name\",\"path\":\"/path/to/foo.feature\",\"content\":[\"Scenario: Foo scenario name\"],\"result\":\"found\"}",
                response.getContent()
        );
    }

    @Test public void
    overwriteScenarioResultWithTheNewOne() {
        Scenario scenario = getScenarioWithResult(ResultStatus.PASSED);
        ScenarioResponse response = new ScenarioResponse(scenario, ResultStatus.AMBIGUOUS);
        assertResult(ResultStatus.AMBIGUOUS, response);
    }

    private Scenario scenario() {
        return getScenarioWithResult(ResultStatus.PASSED);
    }

    @Test public void
    returnOKHttpStatusForPositiveResults() {
        assertHttpStatusForScenarioStatus(HttpStatus.OK, ResultStatus.PASSED);
        assertHttpStatusForScenarioStatus(HttpStatus.OK, ResultStatus.FOUND);
    }

    @Test public void
    returnOKHttpStatusForResponsesThatValidatesDocumentationEvenIfTheValidationIsNegative() {
        assertHttpStatusForScenarioStatus(HttpStatus.OK, ResultStatus.IGNORED);
        assertHttpStatusForScenarioStatus(HttpStatus.OK, ResultStatus.SKIPPED);
        assertHttpStatusForScenarioStatus(HttpStatus.OK, ResultStatus.FAILED);
    }

    @Test public void
    returnNotFoundHttpStatusForNotFoundResults() {
        assertHttpStatusForScenarioStatus(HttpStatus.NOT_FOUND, ResultStatus.NOT_FOUND);
    }

    @Test public void
    returnUnprocessableEntityHttpStatusWhenProblemWithScenarioOccurred() {
        assertHttpStatusForScenarioStatus(HttpStatus.UNPROCESSABLE_ENTITY, ResultStatus.AMBIGUOUS);
        assertHttpStatusForScenarioStatus(HttpStatus.UNPROCESSABLE_ENTITY, ResultStatus.UNKNOWN);
    }

    private void assertHttpStatusForScenarioStatus(HttpStatus expectedHttpStatus, ResultStatus originalResponseStatus) {
        Assert.assertEquals(expectedHttpStatus, responseWithScenarioStatus(originalResponseStatus).getHttpStatus());
    }

    private ScenarioResponse responseWithScenarioStatus(ResultStatus status) {
        return new ScenarioResponse(scenario(), status);
    }


    private Scenario getScenario(final String name, final String content, final ResultStatus status, final String featurePath) {
        return new Scenario() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public Feature getFeature() {
                    return null;
                }

                @Override
                public List<String> getContent() {
                    return Arrays.asList(content);
                }

                @Override
                public ResultStatus getResult() {
                    return status;
                }

                public String getFeaturePath() {
                    return featurePath;
                }
            };
    }



    private Scenario getScenarioWithResult(ResultStatus status) {
        return getScenario("Some scenario name", "Scenario: Some scenario name", status, "/some/path/foo.feature");
    }


    private void assertResult(ResultStatus expectedResult, ScenarioResponse response) {
        Assert.assertEquals(expectedResult.toString(), JsonObject.readFrom(response.getContent()).get("result").asString());
    }

}
