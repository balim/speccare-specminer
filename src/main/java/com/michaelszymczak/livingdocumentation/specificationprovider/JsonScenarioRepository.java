package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;

public class JsonScenarioRepository implements ScenarioRepository {
    public JsonScenarioRepository(ObjectScenarioRepository objectRepository) {

    }

    @Override
    public Scenario find(String partOfScenarioName) throws IOException {
        return null;
    }
}
