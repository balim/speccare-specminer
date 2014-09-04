package com.michaelszymczak.speccare.specminer.domain;

import com.michaelszymczak.speccare.specminer.specificationprovider.DeterminableStub;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScenarioFinalResultShould {

    @Test public void
    produceResponseBasedOnScenarioFoundUsingProvidedText() throws IOException {
        ScenarioFinalResult result = new ScenarioFinalResult(
                repositoryFindingScenarioByGivenKey("Foo", ScenarioStub.use().withContent("Scenario: Foo scenario").build()),
                DeterminableStub.buildReturningStatus(ResultStatus.UNKNOWN)
        );

        ScenarioResponse response = result.createResponse("Foo");

        Assert.assertTrue(response.getContent().contains("\"content\":[\"Scenario: Foo scenario\"]"));
    }

    @Test public void
    useResultDirectlyFromScenarioFoundInScenarioRepositoryIfTheResultIsOtherThanFound() throws IOException {
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.FAILED);
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.NOT_FOUND);
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.IGNORED);
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.SKIPPED);
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.AMBIGUOUS);
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.UNKNOWN);
        assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus.PASSED);
    }

    @Test public void
    askExaminedScenarioResultsForFinalResultIfScenarioHasFoundResult() throws IOException {
        ScenarioFinalResult finalResult = new ScenarioFinalResult(
                repositoryReturningScenarioWIthStatus(ResultStatus.FOUND),
                DeterminableStub.buildReturningStatus(ResultStatus.PASSED)
        );

        Assert.assertEquals(ResultStatus.PASSED, finalResult.createResponse("some scenario").getStatus());
    }

    @Test public void
    useScenarioFullNameToFindItInExaminedScenarioResults() throws IOException {
        Map<String, ResultStatus> examinedScenarioStatuses = new HashMap<>();
        examinedScenarioStatuses.put("Bar", ResultStatus.UNKNOWN);
        examinedScenarioStatuses.put("Foo Bar scenario", ResultStatus.FAILED);


        ScenarioFinalResult result = new ScenarioFinalResult(
                repositoryFindingScenarioByGivenKey("Bar", ScenarioStub.use().withResult(ResultStatus.FOUND).withName("Foo Bar scenario").build()),
                DeterminableStub.buildReturningStatusForScenarioName(examinedScenarioStatuses)
        );

        Assert.assertEquals(ResultStatus.FAILED, result.createResponse("Bar").getStatus());
    }

    private void assertResponseWithSameStatusAsScenarioFromRepository(ResultStatus expectedResultStatus) throws IOException {
        ScenarioFinalResult finalResult = new ScenarioFinalResult(
                repositoryReturningScenarioWIthStatus(expectedResultStatus),
                DeterminableStub.buildReturningStatus(ResultStatus.UNKNOWN)
        );

        Assert.assertEquals(expectedResultStatus, finalResult.createResponse("whatever").getStatus());
    }

    private ScenarioRepository repositoryReturningScenarioWIthStatus(final ResultStatus scenarioResult) {
        return new ScenarioRepository() {
                @Override
                public Scenario find(String partOfScenarioName) throws IOException {
                    return ScenarioStub.use().withResult(scenarioResult).build();
                }
            };
    }

    private ScenarioRepository repositoryFindingScenarioByGivenKey(final String key, final Scenario returnedScenario) {
        return new ScenarioRepository() {
            @Override
            public Scenario find(String partOfScenarioName) throws IOException {
                if (key.equals(partOfScenarioName)) {
                    return returnedScenario;
                }
                throw new IOException(partOfScenarioName + " not found");
            }
        };
    }

}
