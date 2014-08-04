package com.michaelszymczak.speccare.specminer.domain;

import java.util.Collections;
import java.util.List;

public class NotFoundScenario extends Scenario {
    @Override
    public String getName() {
        return "Scenario not found";
    }

    @Override
    public Feature getFeature() {
        return Feature.getNotFound();
    }

    @Override
    public List<String> getContent() {
        return Collections.emptyList();
    }

    @Override
    public String getResult() { return "notfound"; }
}
