package com.michaelszymczak.speccare.specminer.repository;

import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.core.ExistingScenarioBuilder;
import com.michaelszymczak.speccare.specminer.specificationprovider.ScenarioProviderStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProviderBasedScenarioRepositoryShould {
    @Test public void
    returnScenarioMatchingSearchedPhrase() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("Scenario: foo");
        List<Scenario> foundScenarios = repository.findByNameFragment("foo");
        assertContentOfEachFoundScenarioIs(foundScenarios, "Scenario: foo");
    }

    @Test public void
    returnScenarioWhenOnlyPartOfTheNameFound() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("Scenario: foobar");
        List<Scenario> foundScenarios = repository.findByNameFragment("oba");
        assertContentOfEachFoundScenarioIs(foundScenarios, "Scenario: foobar");
    }

    @Test public void
    beCaseInsensitive() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("Scenario: Foobar");
        List<Scenario> foundScenarios = repository.findByNameFragment("fooBar");
        assertContentOfEachFoundScenarioIs(foundScenarios, "Scenario: Foobar");
    }

    @Test public void
    ignoreScenariosNotMatchingThePhrase() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("Scenario: foo", "Scenario: bar", "Scenario: baz");
        List<Scenario> foundScenarios = repository.findByNameFragment("bar");
        assertContentOfEachFoundScenarioIs(foundScenarios, "Scenario: bar");
    }

    @Test public void
    returnAllMatchingScenarios() throws IOException {
        configureProviderToHaveScenarioOfGivenNames("Scenario: foo A", "Scenario: bar B", "Scenario: foo C");
        List<Scenario> foundScenarios = repository.findByNameFragment("foo");
        assertContentOfEachFoundScenarioIs(foundScenarios, "Scenario: foo A", "Scenario: foo C");
    }

    private void assertContentOfEachFoundScenarioIs(List<Scenario> foundScenarios, String... eachScenarioContent) {
        int scenarioNo = 0;
        for (String firstLine : eachScenarioContent) {
            assertEquals(firstLine, foundScenarios.get(scenarioNo++).getContent().get(0));
        }
    }

    private void configureProviderToHaveScenarioOfGivenNames(String... names) {
        provider.scenarios = new ArrayList<>();
        for (String name : names) {
            provider.scenarios.add(ExistingScenarioBuilder.use().withContent(name).build());
        }
    }

    private ScenarioProviderStub provider;
    ProviderBasedScenarioRepository repository;
    @Before public void setUp() {
        provider = new ScenarioProviderStub();
        repository = new ProviderBasedScenarioRepository(provider);
    }
}
