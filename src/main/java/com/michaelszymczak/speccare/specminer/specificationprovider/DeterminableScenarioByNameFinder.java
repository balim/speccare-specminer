package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.AmbiguousScenario;
import com.michaelszymczak.speccare.specminer.core.Determinable;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.repository.ProviderBasedScenarioRepository;
import com.michaelszymczak.speccare.specminer.repository.ScenarioRepository;

import java.io.IOException;
import java.util.List;

public class DeterminableScenarioByNameFinder implements Determinable {
    private final ScenarioRepository repository;
    public DeterminableScenarioByNameFinder(ScenarioRepository repository) {
        this.repository = repository;
    }
    public DeterminableScenarioByNameFinder(ScenarioProvider sc) {
        repository = new ProviderBasedScenarioRepository(sc);
    }

    public Scenario determine(Scenario soughtScenario) throws IOException {
        return returnOne(repository.findByNameFragment(soughtScenario.getName()));
    }

    private Scenario returnOne(List<Scenario> foundScenarios) {
        if (foundScenarios.size() > 1) {
            return new AmbiguousScenario(foundScenarios);
        }
        if (foundScenarios.isEmpty()) {
            return Scenario.getNotFound();
        }
        return foundScenarios.remove(0);
    }
}
