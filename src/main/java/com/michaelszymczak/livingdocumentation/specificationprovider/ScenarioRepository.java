package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ScenarioRepository {
    ScenariosCreatable sc;
    public ScenarioRepository(ScenariosCreatable sc) {
        this.sc = sc;
    }

    public Scenario find(String partOfScenarioName) throws IOException {
        List<Scenario> foundScenarios = new ArrayList<>();
        for(Scenario scenario : sc.create()) {
            if (scenario.getName().toLowerCase().contains(partOfScenarioName.toLowerCase())) {
                foundScenarios.add(scenario);
            }
        }
        throwExceptionIfScenarioNotFound(partOfScenarioName, foundScenarios);
        throwExceptionIfMoreThanOneScenarioFound(partOfScenarioName, foundScenarios);
        return  foundScenarios.remove(0);
    }

    private void throwExceptionIfMoreThanOneScenarioFound(String partOfScenarioName, List<Scenario> foundScenarios) {
        if (foundScenarios.size() > 1) {
            StringBuilder message = new StringBuilder();
            message.append("More than one matching scenario found for the phrase '").append(partOfScenarioName)
                    .append("'. Change your searched phrase or correct the scenario files. Scenarios found:");
            for(Scenario scenario : foundScenarios) {
                message.append("\n").append(scenario.getName()).append(" in ").append(scenario.getFeature().getPath());
            }

            throw new TooManyScenariosFound(message.toString());
        }
    }

    private void throwExceptionIfScenarioNotFound(String partOfScenarioName, List<Scenario> found) {
        if (found.size() == 0) {
            throw new ScenarioNotFoundException("Scenario with a title containing phrase '" + partOfScenarioName + "' not found");
        }
    }
}
