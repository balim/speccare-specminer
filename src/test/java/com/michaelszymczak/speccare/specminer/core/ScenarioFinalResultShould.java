package com.michaelszymczak.speccare.specminer.core;

import com.michaelszymczak.speccare.specminer.cucumber.DeterminableCumulativeCucumberReportStub;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.michaelszymczak.speccare.specminer.core.ResultStatus.*;

public class ScenarioFinalResultShould {

    @Test public void
    produceResponseBasedOnScenarioFoundUsingProvidedText() throws IOException {
        ScenarioFinalResult result = new ScenarioFinalResult(
                repositoryFindingScenarioByGivenKey("Foo", ScenarioStub.use().withContent("Scenario: Foo scenario").build()),
                DeterminableCumulativeCucumberReportStub.buildReturningStatus(UNKNOWN)
        );

        ScenarioResponse response = result.createResponse("Foo");

        Assert.assertTrue(response.getContent().contains("\"content\":[\"Scenario: Foo scenario\"]"));
    }

    @Test public void
    useResultDirectlyFromScenarioFoundInScenarioRepositoryIfTheResultIsOtherThanFound() throws IOException {
        assertResponseWithSameStatusAsScenarioFromRepository(FAILED);
        assertResponseWithSameStatusAsScenarioFromRepository(NOT_FOUND);
        assertResponseWithSameStatusAsScenarioFromRepository(IGNORED);
        assertResponseWithSameStatusAsScenarioFromRepository(SKIPPED);
        assertResponseWithSameStatusAsScenarioFromRepository(AMBIGUOUS);
        assertResponseWithSameStatusAsScenarioFromRepository(UNKNOWN);
        assertResponseWithSameStatusAsScenarioFromRepository(PASSED);
    }

    @Test public void
    askExaminedScenarioResultsForFinalResultIfScenarioHasFoundResult() throws IOException {
        ScenarioFinalResult finalResult = new ScenarioFinalResult(
                repositoryReturningScenarioWIthStatus(FOUND),
                DeterminableCumulativeCucumberReportStub.buildReturningStatus(PASSED)
        );

        Assert.assertEquals(PASSED, finalResult.createResponse("some scenario").getStatus());
    }

    @Test public void
    useScenarioFullNameToFindItInExaminedScenarioResults() throws IOException {
        Map<String, ResultStatus> examinedScenarioStatuses = new HashMap<>();
        examinedScenarioStatuses.put("Bar", UNKNOWN);
        examinedScenarioStatuses.put("Foo Bar scenario", FAILED);


        ScenarioFinalResult result = new ScenarioFinalResult(
                repositoryFindingScenarioByGivenKey("Bar", ScenarioStub.use().withResult(FOUND).withName("Foo Bar scenario").build()),
                DeterminableCumulativeCucumberReportStub.buildReturningStatusForScenarioName(examinedScenarioStatuses)
        );

        Assert.assertEquals(FAILED, result.createResponse("Bar").getStatus());
    }

    private void assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus expectedResultStatus) throws IOException {
        ScenarioFinalResult finalResult = new ScenarioFinalResult(
                repositoryReturningScenarioWIthStatus(expectedResultStatus),
                DeterminableCumulativeCucumberReportStub.buildReturningStatus(UNKNOWN)
        );

        Assert.assertEquals(expectedResultStatus, finalResult.createResponse("whatever").getStatus());
    }

    private Determinable repositoryReturningScenarioWIthStatus(final ResultStatus scenarioResult) {
        return new Determinable() {
            @Override
            public Scenario determine(Scenario soughtScenario) throws IOException {
                return ScenarioStub.use().withResult(scenarioResult).build();
            }
        };
    }

    private Determinable repositoryFindingScenarioByGivenKey(final String key, final Scenario returnedScenario) {
        return new Determinable() {
            @Override
            public Scenario determine(Scenario soughtScenario) throws IOException {
                if (key.equals(soughtScenario.getName())) {
                    return returnedScenario;
                }
                throw new IOException(soughtScenario.getName() + " not found");
            }
        };
    }

}
