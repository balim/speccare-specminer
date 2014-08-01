package com.michaelszymczak.speccare.specminer.domain;

import java.io.IOException;

public interface ScenarioRepository {
    Scenario find(String partOfScenarioName) throws IOException;
}
