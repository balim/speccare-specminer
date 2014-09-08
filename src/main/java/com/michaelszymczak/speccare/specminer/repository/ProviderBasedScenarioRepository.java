package com.michaelszymczak.speccare.specminer.repository;


import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.featurefiles.ScenarioProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProviderBasedScenarioRepository implements ScenarioRepository {
    private final ScenarioProvider provider;

    public ProviderBasedScenarioRepository(ScenarioProvider provider) {
        this.provider = provider;
    }

    public List<Scenario> findByNameFragment(String fragment) throws IOException {
        List<Scenario> found = new ArrayList<>();
        for (Scenario scenario : this.provider.create()) {
            if (scenario.getName().toLowerCase().contains(fragment.toLowerCase())) {
                found.add(scenario);
            }
        }
        return found;
    }
}
