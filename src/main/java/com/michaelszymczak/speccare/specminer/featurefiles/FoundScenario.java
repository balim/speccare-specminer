package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.ResultStatus;
import com.michaelszymczak.speccare.specminer.core.Scenario;
import com.michaelszymczak.speccare.specminer.core.Feature;

import java.util.List;

class FoundScenario extends Scenario {
    FoundScenario(String name, List<String> scenarioContent, Feature wrappingFeature) {
        super(name, wrappingFeature, scenarioContent, ResultStatus.FOUND);
    }
}
