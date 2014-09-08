package com.michaelszymczak.speccare.specminer.repository;

import com.michaelszymczak.speccare.specminer.core.ScenarioBuilder;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.featurefiles.ScenarioProviderStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProviderBasedScenarioRepositoryShould {
    @Test public void
    returnScenarioMatchingSearchedPhrase() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("foo");
        List<Scenario> foundScenarios = repository.findByNameFragment("foo");
        assertContentOfEachFoundScenarioIs(foundScenarios, "foo");
    }

    @Test public void
    returnScenarioWhenOnlyPartOfTheNameFound() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("foobar");
        List<Scenario> foundScenarios = repository.findByNameFragment("oba");
        assertContentOfEachFoundScenarioIs(foundScenarios, "foobar");
    }

    @Test public void
    beCaseInsensitive() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("Foobar");
        List<Scenario> foundScenarios = repository.findByNameFragment("fooBar");
        assertContentOfEachFoundScenarioIs(foundScenarios, "Foobar");
    }

    @Test public void
    ignoreScenariosNotMatchingThePhrase() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("foo", "bar", "baz");
        List<Scenario> foundScenarios = repository.findByNameFragment("bar");
        assertContentOfEachFoundScenarioIs(foundScenarios, "bar");
    }

    @Test public void
    returnAllMatchingScenarios() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("foo A", "bar B", "foo C");
        List<Scenario> foundScenarios = repository.findByNameFragment("foo");
        assertContentOfEachFoundScenarioIs(foundScenarios, "foo A", "foo C");
    }

    private void assertContentOfEachFoundScenarioIs(List<Scenario> foundScenarios, String... eachScenarioContent) {
        int scenarioNo = 0;
        for (String firstLine : eachScenarioContent) {
            assertEquals(firstLine, foundScenarios.get(scenarioNo++).getName());
        }
    }

    private void configureProviderToHaveScenarioOfGivenNames(String... names) {
        provider.scenarios = new ArrayList<>();
        for (String name : names) {
            provider.scenarios.add(ScenarioBuilder.use().withName(name).build());
        }
    }

    private ScenarioProviderStub provider;
    ProviderBasedScenarioRepository repository;
    @Before public void setUp() {
        provider = new ScenarioProviderStub();
        repository = new ProviderBasedScenarioRepository(provider);
    }
}
