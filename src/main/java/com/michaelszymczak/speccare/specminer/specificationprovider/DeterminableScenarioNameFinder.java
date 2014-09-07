package com.michaelszymczak.speccare.specminer.specificationprovider;

import com.michaelszymczak.speccare.specminer.core.AmbiguousScenario;
import com.michaelszymczak.speccare.specminer.core.Determinable;
import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeterminableScenarioNameFinder implements Determinable {
    private final ScenariosCreatable sc;
    public DeterminableScenarioNameFinder(ScenariosCreatable sc) {
        this.sc = sc;
    }

    public Scenario determine(Scenario soughtScenario) throws IOException {
        return returnOne(findScenariosByNameCaseInsensitive(soughtScenario.getName()));
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
