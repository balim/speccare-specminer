package com.michaelszymczak.speccare.specminer.core;

import java.util.Collections;

public class SoughtScenario extends Scenario {

    public SoughtScenario(String name) {
        super(name, Feature.getEmpty(), Collections.<String>emptyList(), ResultStatus.UNKNOWN);
    }
}
