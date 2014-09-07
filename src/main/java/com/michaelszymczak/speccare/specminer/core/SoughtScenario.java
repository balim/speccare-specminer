package com.michaelszymczak.speccare.specminer.core;

import java.util.Collections;
import java.util.List;

public class SoughtScenario extends Scenario {

    private final String name;

    public SoughtScenario(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    public ResultStatus getResult() {
        return ResultStatus.AMBIGUOUS;
    }
}
