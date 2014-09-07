package com.michaelszymczak.speccare.specminer.core;

import java.io.IOException;

public interface Determinable {
    Scenario determine(Scenario soughtScenario) throws IOException;
}
