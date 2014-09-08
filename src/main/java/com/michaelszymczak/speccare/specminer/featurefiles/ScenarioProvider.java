package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.io.IOException;
import java.util.List;

public interface ScenarioProvider {
    List<Scenario> create() throws IOException;
}
