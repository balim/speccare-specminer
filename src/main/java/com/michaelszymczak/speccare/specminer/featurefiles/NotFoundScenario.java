package com.michaelszymczak.speccare.specminer.featurefiles;

import com.michaelszymczak.speccare.specminer.core.ResultStatus;
import com.michaelszymczak.speccare.specminer.core.Scenario;

import java.util.Collections;

class NotFoundScenario extends Scenario {

    private static final NotFoundScenario NOT_FOUND_SCENARIO = new NotFoundScenario();

    private NotFoundScenario() {
        super("Scenario not found", NotFoundFeature.getInstance(), Collections.<String>emptyList(), ResultStatus.NOT_FOUND);
    }

    public static NotFoundScenario getInstance() {
        return NOT_FOUND_SCENARIO;
    }
}
