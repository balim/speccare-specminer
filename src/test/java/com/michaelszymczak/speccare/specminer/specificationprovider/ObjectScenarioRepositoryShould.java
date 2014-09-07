package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.AmbiguousScenario;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.core.SoughtScenario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class ObjectScenarioRepositoryShould {

    @Test public void findScenarioByName() throws IOException {
        givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively(
                "Scenario: Foo", "Scenario: Bar", "Scenario: Baz"
        );
        Assert.assertEquals("Bar", determineFromScenarioWIthName("Bar").getName());
    }

    private Scenario determineFromScenarioWIthName(String searchedFragment) throws IOException {
        return repository.determine(new SoughtScenario(searchedFragment));
    }



    @Test public void findScenarioByTheWordPresentInTheName() throws IOException {
        givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively(
                "Scenario: Foo", "Scenario: Bar ", "Scenario: Some cool scenario"
        );
        Assert.assertEquals("Some cool scenario", determineFromScenarioWIthName("cool").getName());
    }

    @Test public void findScenarioByTheWordFragmentPresentInTheName() throws IOException {
        givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively(
                "Scenario: Foo", "Scenario: Yet another cool scenario"
        );
        Assert.assertEquals("Yet another cool scenario", determineFromScenarioWIthName("oth").getName());
    }

    @Test public void beCaseInsensitiveWhenFindingScenario() throws IOException {
        givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively(
                "Scenario: FOO Scenario", "Scenario: Bar"
        );
        Assert.assertEquals("FOO Scenario", determineFromScenarioWIthName("foo").getName());
    }


    @Test public void returnNotFoundScenarioIfNoScenarioFound() throws IOException {
        givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively("Scenario: Foo");
        Assert.assertSame(Scenario.getNotFound(), determineFromScenarioWIthName("Bar"));
    }

    @Test public void throwExceptionIfMoreThanOneScenarioFound() throws IOException {
        givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively(
                "Scenario: Foo 1", "Scenario: Fooo 2", "Scenario: Bar", "Scenario: Foo scenario 3"
        );
        Scenario scenario = determineFromScenarioWIthName("Foo");
        Assert.assertTrue(scenario instanceof AmbiguousScenario);
    }

    private void givenScenarioCreatorThatReturnsScenariosAndTheirFirstLinesAreRespectively(String... firstLines) {
        for(String scenarioFirstLine : firstLines) {
            sc.scenarios.add(ExistingScenarioBuilder.use().withContent(scenarioFirstLine).build());
        }
    }

    private ScenariosCreatorStub sc;
    private DeterminableScenarioNameFinder repository;

    @Rule public ExpectedException thrown = ExpectedException.none();
    @Before public void setUp() {
        sc = new ScenariosCreatorStub();
        repository = new DeterminableScenarioNameFinder(sc);
    }
}
