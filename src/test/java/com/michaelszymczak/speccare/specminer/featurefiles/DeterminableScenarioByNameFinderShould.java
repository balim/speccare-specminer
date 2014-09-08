package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.AmbiguousScenario;
import com.michaelszymczak.speccare.specminer.core.ExistingScenarioBuilder;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.core.SoughtScenario;
import com.michaelszymczak.speccare.specminer.repository.ScenarioRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class DeterminableScenarioByNameFinderShould {

    @Test public void findScenarioByName() throws IOException {
        givenRepositoryReturningNumberOfScenariosForTheFooPhrase(1);
        Assert.assertSame(repository.scenariosUnderFooPhrase.get(0), determineUsingSoughtScenarioWithGivenName("Foo"));
    }

    @Test public void returnNotFoundScenarioIfNoScenarioFound() throws IOException {
        givenRepositoryReturningNumberOfScenariosForTheFooPhrase(0);
        Assert.assertSame(Scenario.getNotFound(), determineUsingSoughtScenarioWithGivenName("Foo"));
    }

    @Test public void returnNotFoundScenarioIfNoScenarioMatchesSearchedPhrase() throws IOException {
        givenRepositoryReturningNumberOfScenariosForTheFooPhrase(1);
        Assert.assertSame(Scenario.getNotFound(), determineUsingSoughtScenarioWithGivenName("Bar"));
    }

    @Test public void returnAmbiguousScenarioIfMoreThanOneScenarioFound() throws IOException {
        givenRepositoryReturningNumberOfScenariosForTheFooPhrase(2);
        Assert.assertTrue(determineUsingSoughtScenarioWithGivenName("Foo") instanceof AmbiguousScenario);
    }

    private DeterminableScenarioByNameFinder determinable;
    private FooPhraseScenarioRepositoryStub repository;

    @Before public void setUp() {
        repository = new FooPhraseScenarioRepositoryStub();
        determinable = new DeterminableScenarioByNameFinder(repository);
    }

    private Scenario determineUsingSoughtScenarioWithGivenName(String searchedFragment) throws IOException {
        return determinable.determine(new SoughtScenario(searchedFragment));
    }

    private void givenRepositoryReturningNumberOfScenariosForTheFooPhrase(int numberOfFoundScenarios) {
        for (int i = 0; i < numberOfFoundScenarios; i++) {
            repository.scenariosUnderFooPhrase.add(new ExistingScenarioBuilder().build());
        }
    }

    private static class FooPhraseScenarioRepositoryStub implements ScenarioRepository {
        public List<Scenario> scenariosUnderFooPhrase = new ArrayList<>();

        @Override
        public List<Scenario> findByNameFragment(String fragment) throws IOException {
            if ("Foo".equals(fragment)) {
                return scenariosUnderFooPhrase;
            } else {
                return Collections.emptyList();
            }
        }
    }
}
