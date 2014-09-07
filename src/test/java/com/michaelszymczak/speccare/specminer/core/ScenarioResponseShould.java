package com.michaelszymczak.speccare.specminer.core;

import com.eclipsesource.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class ScenarioResponseShould {

    @Test public void
    returnContentUsingScenarioData() {
        Scenario scenario = getScenario("Foo scenario name", "Scenario: Foo scenario name", FOUND, "/path/to/foo.feature");

        ScenarioResponse response = new ScenarioResponse(scenario, FOUND);

        Assert.assertEquals(
                "{\"name\":\"Foo scenario name\",\"path\":\"/path/to/foo.feature\",\"content\":[\"Scenario: Foo scenario name\"],\"result\":\"found\"}",
                response.getContent()
        );
    }

    @Test public void
    useScenarioResultAsIsIfNoOtherResultPassed() {
        Scenario scenario = getScenarioWithResult(NOT_FOUND);
        ScenarioResponse response = new ScenarioResponse(scenario);
        assertResult(NOT_FOUND, response);
    }

    @Test public void
    overwriteScenarioResultWithTheNewOneIfPassed() {
        Scenario scenario = getScenarioWithResult(PASSED);
        ScenarioResponse response = new ScenarioResponse(scenario, AMBIGUOUS);
        assertResult(AMBIGUOUS, response);
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

    private Scenario scenario() {
        return getScenarioWithResult(PASSED);
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
