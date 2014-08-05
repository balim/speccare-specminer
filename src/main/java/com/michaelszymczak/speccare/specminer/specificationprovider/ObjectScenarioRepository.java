package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.domain.AmbiguousScenario;
import com.michaelszymczak.speccare.specminer.domain.Scenario;
import com.michaelszymczak.speccare.specminer.domain.ScenarioRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectScenarioRepository implements ScenarioRepository {
    private final ScenariosCreatable sc;
    public ObjectScenarioRepository(ScenariosCreatable sc) {
        this.sc = sc;
    }

    @Override
    public Scenario find(String partOfScenarioName) throws IOException {
        return returnOne(findScenariosByNameCaseInsensitive(partOfScenarioName));
    }

    private List<Scenario> findScenariosByNameCaseInsensitive(String partOfScenarioName) throws IOException {
        List<Scenario> foundScenarios = new ArrayList<>();
        for(Scenario scenario : sc.create()) {
            if (scenario.getName().toLowerCase().contains(partOfScenarioName.toLowerCase())) {
                foundScenarios.add(scenario);
            }
        }
        return foundScenarios;
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
