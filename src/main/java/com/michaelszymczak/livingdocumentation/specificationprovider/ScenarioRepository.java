package com.michaelszymczak.livingdocumentation.specificationprovider;

import java.io.IOException;

public interface ScenarioRepository {
    Scenario find(String partOfScenarioName) throws IOException;
}
